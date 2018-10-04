
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class PQInner
extends TLObject {
    public static final int CLASS_ID = -2083955988;
    protected byte[] pq;
    protected byte[] p;
    protected byte[] q;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected byte[] newNonce;

    public PQInner(byte[] pq, byte[] p, byte[] q, byte[] nonce, byte[] serverNonce, byte[] newNonce) {
        this.pq = pq;
        this.p = p;
        this.q = q;
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.newNonce = newNonce;
    }

    public PQInner() {
    }

    @Override
    public int getClassId() {
        return -2083955988;
    }

    @Override
    public String toString() {
        return "pQInner#83c95aec";
    }

    public byte[] getPq() {
        return this.pq;
    }

    public byte[] getP() {
        return this.p;
    }

    public byte[] getQ() {
        return this.q;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public byte[] getNewNonce() {
        return this.newNonce;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.pq, stream);
        StreamingUtils.writeTLBytes(this.p, stream);
        StreamingUtils.writeTLBytes(this.q, stream);
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeByteArray(this.newNonce, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pq = StreamingUtils.readTLBytes(stream);
        this.p = StreamingUtils.readTLBytes(stream);
        this.q = StreamingUtils.readTLBytes(stream);
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.newNonce = StreamingUtils.readBytes(32, stream);
    }
}

