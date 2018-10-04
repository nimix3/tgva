
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.pq.ResPQ;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class ReqPQ
extends TLMethod<ResPQ> {
    public static final int CLASS_ID = 1615239032;
    protected byte[] nonce;

    public ReqPQ(byte[] nonce) {
        if (nonce == null || nonce.length != 16) {
            throw new IllegalArgumentException("nonce might be not null and 16 bytes length");
        }
        this.nonce = nonce;
    }

    public ReqPQ() {
    }

    @Override
    public ResPQ deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject response = context.deserializeMessage(stream);
        if (response == null) {
            throw new DeserializeException("Unable to deserialize response");
        }
        if (!(response instanceof ResPQ)) {
            throw new DeserializeException("Response has incorrect type");
        }
        return (ResPQ)response;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public void setNonce(byte[] nonce) {
        if (nonce == null || nonce.length != 16) {
            throw new IllegalArgumentException("nonce might be not null and 16 bytes length");
        }
        this.nonce = nonce;
    }

    @Override
    public int getClassId() {
        return 1615239032;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
    }

    @Override
    public String toString() {
        return "req_pq#60469778";
    }
}

