
package org.telegram.mtproto.pq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Socket;
import org.telegram.mtproto.ServerException;
import org.telegram.mtproto.TransportSecurityException;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.pq.PqAuth;
import org.telegram.mtproto.secure.CryptoUtils;
import org.telegram.mtproto.secure.Entropy;
import org.telegram.mtproto.secure.Keys;
import org.telegram.mtproto.secure.pq.PQSolver;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.time.TimeOverlord;
import org.telegram.mtproto.tl.pq.ClientDhInner;
import org.telegram.mtproto.tl.pq.DhGenFailure;
import org.telegram.mtproto.tl.pq.DhGenOk;
import org.telegram.mtproto.tl.pq.DhGenResult;
import org.telegram.mtproto.tl.pq.DhGenRetry;
import org.telegram.mtproto.tl.pq.PQInner;
import org.telegram.mtproto.tl.pq.ReqDhParams;
import org.telegram.mtproto.tl.pq.ReqPQ;
import org.telegram.mtproto.tl.pq.ReqSetDhClientParams;
import org.telegram.mtproto.tl.pq.ResPQ;
import org.telegram.mtproto.tl.pq.ServerDhFailure;
import org.telegram.mtproto.tl.pq.ServerDhInner;
import org.telegram.mtproto.tl.pq.ServerDhOk;
import org.telegram.mtproto.tl.pq.ServerDhParams;
import org.telegram.mtproto.tl.pq.TLInitContext;
import org.telegram.mtproto.transport.ConnectionType;
import org.telegram.mtproto.transport.PlainTcpConnection;
import org.telegram.mtproto.transport.TransportRate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class Authorizer {
    private static final String TAG = "Authorizer";
    private static final int AUTH_ATTEMPT_COUNT = 5;
    private static final int AUTH_RETRY_COUNT = 5;
    private PlainTcpConnection context;
    private TLInitContext initContext = new TLInitContext();

    private <T extends TLObject> T executeMethod(TLMethod<T> object) throws IOException {
        long requestMessageId = TimeOverlord.getInstance().createWeakMessageId();
        long start = System.nanoTime();
        byte[] data = object.serialize();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamingUtils.writeLong(0L, out);
        StreamingUtils.writeLong(requestMessageId, out);
        StreamingUtils.writeInt(data.length, out);
        StreamingUtils.writeByteArray(data, out);
        byte[] response = this.context.executeMethod(out.toByteArray());
        ByteArrayInputStream in = new ByteArrayInputStream(response);
        long authId = StreamingUtils.readLong(in);
        if (authId != 0L) {
            throw new IOException("Auth id might be equal to zero");
        }
        long messageId = StreamingUtils.readLong(in);
        int length = StreamingUtils.readInt(in);
        byte[] messageResponse = StreamingUtils.readBytes(length, in);
        return object.deserializeResponse(messageResponse, (TLContext)this.initContext);
    }

    private PqAuth authAttempt() throws IOException {
        ServerDhInner dhInner;
        byte[] nonce = Entropy.getInstance().generateSeed(16);
        ResPQ resPQ = (ResPQ)this.executeMethod(new ReqPQ(nonce));
        byte[] serverNonce = resPQ.getServerNonce();
        long fingerprint = 0L;
        Keys.Key publicKey = null;
        block2 : for (Long srcFingerprint : resPQ.getFingerprints()) {
            for (Keys.Key key : Keys.AVAILABLE_KEYS) {
                if (!srcFingerprint.equals(key.getFingerprint())) continue;
                fingerprint = srcFingerprint;
                publicKey = key;
                break block2;
            }
        }
        if (fingerprint == 0L) {
            throw new IOException("Unknown public keys");
        }
        BigInteger pq = CryptoUtils.loadBigInt(resPQ.getPq());
        BigInteger p = null;
        try {
            long start = System.currentTimeMillis();
            p = PQSolver.solvePq(pq);
            Logger.d(TAG, "Solved PQ in " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (Exception e) {
            throw new IOException();
        }
        BigInteger q = pq.divide(p);
        byte[] newNonce = Entropy.getInstance().generateSeed(32);
        PQInner inner = new PQInner(resPQ.getPq(), CryptoUtils.fromBigInt(p), CryptoUtils.fromBigInt(q), nonce, serverNonce, newNonce);
        byte[] pqInner = inner.serialize();
        byte[] hash = CryptoUtils.SHA1(pqInner);
        byte[] seed = Entropy.getInstance().generateSeed(255 - hash.length - pqInner.length);
        byte[] dataWithHash = CryptoUtils.concat(hash, pqInner, seed);
        byte[] encrypted = CryptoUtils.RSA(dataWithHash, publicKey.getPublicKey(), publicKey.getExponent());
        long start = System.nanoTime();
        ServerDhParams dhParams = (ServerDhParams)this.executeMethod(new ReqDhParams(nonce, serverNonce, CryptoUtils.fromBigInt(p), CryptoUtils.fromBigInt(q), fingerprint, encrypted));
        long dhParamsDuration = (System.nanoTime() - start) / 1000000L;
        if (dhParams instanceof ServerDhFailure) {
            ServerDhFailure hdFailure = (ServerDhFailure)dhParams;
            if (CryptoUtils.arrayEq(hdFailure.getNewNonceHash(), CryptoUtils.SHA1(newNonce))) {
                throw new ServerException("Received server_DH_params_fail#79cb045d");
            }
            throw new TransportSecurityException("Received server_DH_params_fail#79cb045d with incorrect hash");
        }
        ServerDhOk serverDhParams = (ServerDhOk)dhParams;
        byte[] encryptedAnswer = serverDhParams.getEncryptedAnswer();
        byte[] tmpAesKey = CryptoUtils.concat(CryptoUtils.SHA1(newNonce, serverNonce), CryptoUtils.substring(CryptoUtils.SHA1(serverNonce, newNonce), 0, 12));
        byte[] tmpAesIv = CryptoUtils.concat(CryptoUtils.concat(CryptoUtils.substring(CryptoUtils.SHA1(serverNonce, newNonce), 12, 8), CryptoUtils.SHA1(newNonce, newNonce)), CryptoUtils.substring(newNonce, 0, 4));
        byte[] answer = CryptoUtils.AES256IGEDecrypt(encryptedAnswer, tmpAesIv, tmpAesKey);
        ByteArrayInputStream stream = new ByteArrayInputStream(answer);
        byte[] answerHash = StreamingUtils.readBytes(20, stream);
        if (!CryptoUtils.arrayEq(answerHash, CryptoUtils.SHA1((dhInner = (ServerDhInner)this.initContext.deserializeMessage(stream)).serialize()))) {
            throw new TransportSecurityException();
        }
        TimeOverlord.getInstance().onServerTimeArrived((long)dhInner.getServerTime() * 1000L, dhParamsDuration);
        for (int i = 0; i < 5; ++i) {
            byte[] newNonceHash;
            BigInteger b = CryptoUtils.loadBigInt(Entropy.getInstance().generateSeed(256));
            BigInteger g = new BigInteger("" + dhInner.getG() + "");
            BigInteger dhPrime = CryptoUtils.loadBigInt(dhInner.getDhPrime());
            BigInteger gb = g.modPow(b, dhPrime);
            BigInteger authKeyVal = CryptoUtils.loadBigInt(dhInner.getG_a()).modPow(b, dhPrime);
            byte[] authKey = CryptoUtils.alignKeyZero(CryptoUtils.fromBigInt(authKeyVal), 256);
            byte[] authAuxHash = CryptoUtils.substring(CryptoUtils.SHA1(authKey), 0, 8);
            ClientDhInner clientDHInner = new ClientDhInner(nonce, serverNonce, i, CryptoUtils.fromBigInt(gb));
            byte[] innerData = clientDHInner.serialize();
            byte[] innerDataWithHash = CryptoUtils.align(CryptoUtils.concat(CryptoUtils.SHA1(innerData), innerData), 16);
            byte[] dataWithHashEnc = CryptoUtils.AES256IGEEncrypt(innerDataWithHash, tmpAesIv, tmpAesKey);
            DhGenResult result = (DhGenResult)this.executeMethod(new ReqSetDhClientParams(nonce, serverNonce, dataWithHashEnc));
            if (result instanceof DhGenOk) {
                newNonceHash = CryptoUtils.substring(CryptoUtils.SHA1(newNonce, {1}, authAuxHash), 4, 16);
                if (!CryptoUtils.arrayEq(result.getNewNonceHash(), newNonceHash)) {
                    throw new TransportSecurityException();
                }
                long serverSalt = StreamingUtils.readLong(CryptoUtils.xor(CryptoUtils.substring(newNonce, 0, 8), CryptoUtils.substring(serverNonce, 0, 8)), 0);
                return new PqAuth(authKey, serverSalt, this.context.getSocket());
            }
            if (result instanceof DhGenRetry) {
                newNonceHash = CryptoUtils.substring(CryptoUtils.SHA1(newNonce, {2}, authAuxHash), 4, 16);
                if (CryptoUtils.arrayEq(result.getNewNonceHash(), newNonceHash)) continue;
                throw new TransportSecurityException();
            }
            if (!(result instanceof DhGenFailure)) continue;
            newNonceHash = CryptoUtils.substring(CryptoUtils.SHA1(newNonce, {3}, authAuxHash), 4, 16);
            if (!CryptoUtils.arrayEq(result.getNewNonceHash(), newNonceHash)) {
                throw new TransportSecurityException();
            }
            throw new ServerException();
        }
        throw new ServerException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PqAuth doAuth(ConnectionInfo[] infos) {
        TransportRate rate = new TransportRate(infos);
        for (int i = 0; i < 5; ++i) {
            ConnectionType connectionType = rate.tryConnection();
            try {
                this.context = new PlainTcpConnection(connectionType.getHost(), connectionType.getPort());
                rate.onConnectionSuccess(connectionType.getId());
            }
            catch (IOException e) {
                Logger.e(TAG, e);
                rate.onConnectionFailure(connectionType.getId());
                continue;
            }
            try {
                PqAuth e = this.authAttempt();
                return e;
            }
            catch (IOException e) {
                Logger.e(TAG, e);
            }
            finally {
                if (this.context != null) {
                    this.context.destroy();
                    this.context = null;
                }
            }
            try {
                Thread.sleep(300L);
                continue;
            }
            catch (InterruptedException e) {
                return null;
            }
        }
        return null;
    }
}

