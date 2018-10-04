
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public abstract class DhGenResult
extends TLObject {
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected byte[] newNonceHash;

    protected DhGenResult(byte[] nonce, byte[] serverNonce, byte[] newNonceHash) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.newNonceHash = newNonceHash;
    }

    public DhGenResult() {
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public byte[] getNewNonceHash() {
        return this.newNonceHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeByteArray(this.newNonceHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.newNonceHash = StreamingUtils.readBytes(16, stream);
    }
}

