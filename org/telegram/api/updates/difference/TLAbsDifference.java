
package org.telegram.api.updates.difference;

import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.encrypted.message.TLAbsEncryptedMessage;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsDifference
extends TLObject {
    protected int date;
    protected int seq;
    protected TLVector<TLAbsMessage> newMessages = new TLVector();
    protected TLVector<TLAbsEncryptedMessage> newEncryptedMessages = new TLVector();
    protected TLVector<TLAbsUpdate> otherUpdates = new TLVector();
    protected TLVector<TLAbsChat> chats = new TLVector();
    protected TLVector<TLAbsUser> users = new TLVector();

    protected TLAbsDifference() {
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public TLVector<TLAbsMessage> getNewMessages() {
        return this.newMessages;
    }

    public void setNewMessages(TLVector<TLAbsMessage> newMessages) {
        this.newMessages = newMessages;
    }

    public TLVector<TLAbsEncryptedMessage> getNewEncryptedMessages() {
        return this.newEncryptedMessages;
    }

    public void setNewEncryptedMessages(TLVector<TLAbsEncryptedMessage> newEncryptedMessages) {
        this.newEncryptedMessages = newEncryptedMessages;
    }

    public TLVector<TLAbsUpdate> getOtherUpdates() {
        return this.otherUpdates;
    }

    public void setOtherUpdates(TLVector<TLAbsUpdate> otherUpdates) {
        this.otherUpdates = otherUpdates;
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

