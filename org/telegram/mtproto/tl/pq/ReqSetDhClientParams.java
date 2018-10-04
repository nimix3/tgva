
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.pq.DhGenResult;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class ReqSetDhClientParams
extends TLMethod<DhGenResult> {
    public static final int CLASS_ID = -184262881;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected byte[] encrypted;

    public ReqSetDhClientParams(byte[] nonce, byte[] serverNonce, byte[] encrypted) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.encrypted = encrypted;
    }

    public ReqSetDhClientParams() {
    }

    @Override
    public DhGenResult deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject response = context.deserializeMessage(stream);
        if (response == null) {
            throw new DeserializeException("Unable to deserialize response");
        }
        if (!(response instanceof DhGenResult)) {
            throw new DeserializeException("Response has incorrect type");
        }
        return (DhGenResult)response;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public byte[] getEncrypted() {
        return this.encrypted;
    }

    @Override
    public int getClassId() {
        return -184262881;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeTLBytes(this.encrypted, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.encrypted = StreamingUtils.readTLBytes(stream);
    }

    @Override
    public String toString() {
        return "set_client_DH_params#f5045f1f";
    }
}

