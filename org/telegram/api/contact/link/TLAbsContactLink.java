
package org.telegram.api.contact.link;

import org.telegram.tl.TLObject;

public abstract class TLAbsContactLink
extends TLObject {
    protected boolean contact;

    protected TLAbsContactLink() {
    }

    public boolean isContact() {
        return this.contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }
}

