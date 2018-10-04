
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.pq.ServerDhParams;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class ServerDhFailure
extends ServerDhParams {
    public static final int CLASS_ID = 2043348061;

    public ServerDhFailure(byte[] nonce, byte[] serverNonce, byte[] newNonceHash) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.newNonceHash = newNonceHash;
        this.encryptedAnswer = null;
    }

    public ServerDhFailure() {
        this.nonce = null;
        this.serverNonce = null;
        this.newNonceHash = null;
        this.encryptedAnswer = null;
    }

    @Override
    public int getClassId() {
        return 2043348061;
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

    @Override
    public String toString() {
        return "server_DH_params_fail#2043348061";
    }
}

