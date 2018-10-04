
package org.telegram.api.chat;

import org.telegram.tl.TLObject;

public abstract class TLAbsChat
extends TLObject {
    protected int id;

    protected TLAbsChat() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

