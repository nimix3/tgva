
package org.telegram.api.contacts.blocked;

import org.telegram.api.contact.TLContactBlocked;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsBlocked
extends TLObject {
    protected TLVector<TLContactBlocked> blocked;
    protected TLVector<TLAbsUser> users;

    protected TLAbsBlocked() {
    }

    public TLVector<TLContactBlocked> getBlocked() {
        return this.blocked;
    }

    public void setBlocked(TLVector<TLContactBlocked> value) {
        this.blocked = value;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> value) {
        this.users = value;
    }
}

