
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;

public class ResPQ
extends TLObject {
    public static final int CLASS_ID = 85337187;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected byte[] pq;
    protected TLLongVector fingerprints;

    public ResPQ(byte[] nonce, byte[] serverNonce, byte[] pq, TLLongVector fingerprints) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.pq = pq;
        this.fingerprints = fingerprints;
    }

    public ResPQ() {
    }

    @Override
    public int getClassId() {
        return 85337187;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public void setServerNonce(byte[] serverNonce) {
        this.serverNonce = serverNonce;
    }

    public byte[] getPq() {
        return this.pq;
    }

    public void setPq(byte[] pq) {
        this.pq = pq;
    }

    public TLLongVector getFingerprints() {
        return this.fingerprints;
    }

    public void setFingerprints(TLLongVector fingerprints) {
        this.fingerprints = fingerprints;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeTLBytes(this.pq, stream);
        StreamingUtils.writeTLVector(this.fingerprints, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.pq = StreamingUtils.readTLBytes(stream);
        this.fingerprints = StreamingUtils.readTLLongVector(stream, context);
    }

    @Override
    public String toString() {
        return "resPQ#05162463";
    }
}

