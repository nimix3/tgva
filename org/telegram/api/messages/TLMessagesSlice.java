
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLMessagesSlice
extends TLAbsMessages {
    public static final int CLASS_ID = 189033187;
    private int count;

    @Override
    public int getClassId() {
        return 189033187;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int value) {
        this.count = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.count = StreamingUtils.readInt(stream);
        this.messages = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.messagesSlice#b446ae3";
    }
}

