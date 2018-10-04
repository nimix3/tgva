
package org.telegram.api.messages.dialogs;

import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.dialog.TLAbsDialog;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsDialogs
extends TLObject {
    protected TLVector<TLAbsDialog> dialogs;
    protected TLVector<TLAbsMessage> messages;
    protected TLVector<TLAbsChat> chats;
    protected TLVector<TLAbsUser> users;

    protected TLAbsDialogs() {
    }

    public TLVector<TLAbsDialog> getDialogs() {
        return this.dialogs;
    }

    public void setDialogs(TLVector<TLAbsDialog> value) {
        this.dialogs = value;
    }

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

