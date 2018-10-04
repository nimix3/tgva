
package org.telegram.api.messages.message;

import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsMessage
extends TLObject {
    public org.telegram.api.message.TLAbsMessage message;
    public TLVector<TLAbsChat> chats = new TLVector();
    public TLVector<TLAbsUser> users = new TLVector();

    protected TLAbsMessage() {
    }

    public org.telegram.api.message.TLAbsMessage getMessage() {
        return this.message;
    }

    public void setMessage(org.telegram.api.message.TLAbsMessage message) {
        this.message = message;
    }

    public TLVector<TLAbsChat> getChats() {
        return this.chats;
    }

    public void setChats(TLVector<TLAbsChat> chats) {
        this.chats = chats;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }
}

