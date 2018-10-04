
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.pq.ServerDhParams;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class ServerDhOk
extends ServerDhParams {
    public static final int CLASS_ID = -790100132;

    public ServerDhOk(byte[] nonce, byte[] serverNonce, byte[] encryptedAnswer) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.newNonceHash = null;
        this.encryptedAnswer = encryptedAnswer;
    }

    public ServerDhOk() {
        this.nonce = null;
        this.serverNonce = null;
        this.newNonceHash = null;
        this.encryptedAnswer = null;
    }

    @Override
    public int getClassId() {
        return -790100132;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeTLBytes(this.encryptedAnswer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.encryptedAnswer = StreamingUtils.readTLBytes(stream);
    }

    @Override
    public String toString() {
        return "server_DH_params_ok#d0e8075c";
    }
}

