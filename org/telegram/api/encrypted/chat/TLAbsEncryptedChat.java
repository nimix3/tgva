
package org.telegram.api.encrypted.chat;

import org.telegram.tl.TLObject;

public abstract class TLAbsEncryptedChat
extends TLObject {
    protected int id;

    protected TLAbsEncryptedChat() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }
}

