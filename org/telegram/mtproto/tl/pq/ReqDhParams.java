
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.pq.ServerDhParams;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class ReqDhParams
extends TLMethod<ServerDhParams> {
    public static final int CLASS_ID = -686627650;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected byte[] p;
    protected byte[] q;
    protected long fingerPrint;
    protected byte[] encryptedData;

    public ReqDhParams(byte[] nonce, byte[] serverNonce, byte[] p, byte[] q, long fingerPrint, byte[] encryptedData) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.p = p;
        this.q = q;
        this.fingerPrint = fingerPrint;
        this.encryptedData = encryptedData;
    }

    public ReqDhParams() {
    }

    @Override
    public ServerDhParams deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject response = context.deserializeMessage(stream);
        if (response == null) {
            throw new DeserializeException("Unable to deserialize response");
        }
        if (!(response instanceof ServerDhParams)) {
            throw new DeserializeException("Response has incorrect type");
        }
        return (ServerDhParams)response;
    }

    @Override
    public int getClassId() {
        return -686627650;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public byte[] getP() {
        return this.p;
    }

    public byte[] getQ() {
        return this.q;
    }

    public long getFingerPrint() {
        return this.fingerPrint;
    }

    public byte[] getEncryptedData() {
        return this.encryptedData;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeTLBytes(this.p, stream);
        StreamingUtils.writeTLBytes(this.q, stream);
        StreamingUtils.writeLong(this.fingerPrint, stream);
        StreamingUtils.writeTLBytes(this.encryptedData, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.p = StreamingUtils.readTLBytes(stream);
        this.q = StreamingUtils.readTLBytes(stream);
        this.fingerPrint = StreamingUtils.readLong(stream);
        this.encryptedData = StreamingUtils.readTLBytes(stream);
    }

    @Override
    public String toString() {
        return "req_DH_params#d712e4be";
    }
}

