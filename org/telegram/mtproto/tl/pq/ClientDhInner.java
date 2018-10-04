
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class ClientDhInner
extends TLObject {
    public static final int CLASS_ID = 1715713620;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected long retryId;
    protected byte[] gb;

    public ClientDhInner(byte[] nonce, byte[] serverNonce, long retryId, byte[] gb) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.retryId = retryId;
        this.gb = gb;
    }

    public ClientDhInner() {
    }

    @Override
    public int getClassId() {
        return 1715713620;
    }

    @Override
    public String toString() {
        return "clientDhInner#6643b654";
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public long getRetryId() {
        return this.retryId;
    }

    public byte[] getGb() {
        return this.gb;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeLong(this.retryId, stream);
        StreamingUtils.writeTLBytes(this.gb, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.retryId = StreamingUtils.readLong(stream);
        this.gb = StreamingUtils.readTLBytes(stream);
    }
}

