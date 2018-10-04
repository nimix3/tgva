
package org.telegram.api.user;

import org.telegram.tl.TLObject;

public abstract class TLAbsUser
extends TLObject {
    protected int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

