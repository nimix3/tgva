
package org.telegram.api.messages;

import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsMessages
extends TLObject {
    protected TLVector<TLAbsMessage> messages;
    protected TLVector<TLAbsChat> chats;
    protected TLVector<TLAbsUser> users;

    public TLVector<TLAbsMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(TLVector<TLAbsMessage> value) {
        this.messages = value;
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
}

