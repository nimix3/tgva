
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.api.input.encrypted.TLInputEncryptedChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesAcceptEncryption
extends TLMethod<TLAbsEncryptedChat> {
    public static final int CLASS_ID = 1035731989;
    private TLInputEncryptedChat peer;
    private TLBytes gB;
    private long keyFingerprint;

    @Override
    public int getClassId() {
        return 1035731989;
    }

    @Override
    public TLAbsEncryptedChat deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsEncryptedChat) {
            return (TLAbsEncryptedChat)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.encrypted.chat.TLAbsEncryptedChat, got: " + res.getClass().getCanonicalName());
    }

    public TLInputEncryptedChat getPeer() {
        return this.peer;
    }

    public void setPeer(TLInputEncryptedChat value) {
        this.peer = value;
    }

    public TLBytes getGB() {
        return this.gB;
    }

    public void setGB(TLBytes value) {
        this.gB = value;
    }

    public long getKeyFingerprint() {
        return this.keyFingerprint;
    }

    public void setKeyFingerprint(long value) {
        this.keyFingerprint = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLBytes(this.gB, stream);
        StreamingUtils.writeLong(this.keyFingerprint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLInputEncryptedChat)StreamingUtils.readTLObject(stream, context);
        this.gB = StreamingUtils.readTLBytes(stream, context);
        this.keyFingerprint = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "messages.acceptEncryption#3dbc0415";
    }
}

