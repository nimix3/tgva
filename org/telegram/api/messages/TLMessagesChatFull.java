
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.TLAbsChatFull;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLMessagesChatFull
extends TLObject {
    public static final int CLASS_ID = -438840932;
    private TLAbsChatFull fullChat;
    private TLVector<TLAbsChat> chats = new TLVector();
    private TLVector<TLAbsUser> users = new TLVector();

    @Override
    public int getClassId() {
        return -438840932;
    }

    public TLAbsChatFull getFullChat() {
        return this.fullChat;
    }

    public void setFullChat(TLAbsChatFull value) {
        this.fullChat = value;
    }

    public TLVector<TLAbsChat> getChats() {
        return this.chats;
    }

    public void setChats(TLVector<TLAbsChat> value) {
        this.chats = value;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> value) {
        this.users = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.fullChat, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.fullChat = (TLAbsChatFull)StreamingUtils.readTLObject(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.chatFull#e5d7d19c";
    }
}

