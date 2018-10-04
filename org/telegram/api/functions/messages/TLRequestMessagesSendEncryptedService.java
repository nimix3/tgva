
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.encrypted.TLInputEncryptedChat;
import org.telegram.api.messages.sentencrypted.TLAbsSentEncryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSendEncryptedService
extends TLMethod<TLAbsSentEncryptedMessage> {
    public static final int CLASS_ID = 852769188;
    private TLInputEncryptedChat peer;
    private long randomId;
    private TLBytes data;

    @Override
    public int getClassId() {
        return 852769188;
    }

    @Override
    public TLAbsSentEncryptedMessage deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsSentEncryptedMessage) {
            return (TLAbsSentEncryptedMessage)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.messages.sentencrypted.TLAbsSentEncryptedMessage, got: " + res.getClass().getCanonicalName());
    }

    public TLInputEncryptedChat getPeer() {
        return this.peer;
    }

    public void setPeer(TLInputEncryptedChat value) {
        this.peer = value;
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long value) {
        this.randomId = value;
    }

    public TLBytes getData() {
        return this.data;
    }

    public void setData(TLBytes value) {
        this.data = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeTLBytes(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLInputEncryptedChat)StreamingUtils.readTLObject(stream, context);
        this.randomId = StreamingUtils.readLong(stream);
        this.data = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "messages.sendEncryptedService#32d439a4";
    }
}

