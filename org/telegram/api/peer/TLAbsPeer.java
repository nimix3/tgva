
package org.telegram.api.peer;

import org.telegram.tl.TLObject;

public abstract class TLAbsPeer
extends TLObject {
    protected int id;

    protected TLAbsPeer() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

