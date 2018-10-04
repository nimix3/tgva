
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLMessagesChats
extends TLObject {
    public static final int CLASS_ID = 1694474197;
    protected TLVector<TLAbsChat> chats;

    @Override
    public int getClassId() {
        return 1694474197;
    }

    public TLVector<TLAbsChat> getChats() {
        return this.chats;
    }

    public void setChats(TLVector<TLAbsChat> value) {
        this.chats = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.chats, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chats = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.chats#64ff9fd5";
    }
}

