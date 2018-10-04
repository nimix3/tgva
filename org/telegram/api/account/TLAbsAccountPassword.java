
package org.telegram.api.account;

import org.telegram.tl.TLBytes;
import org.telegram.tl.TLObject;

public abstract class TLAbsAccountPassword
extends TLObject {
    protected TLBytes newSalt;
    protected String emailUnconfirmedPattern;

    protected TLAbsAccountPassword() {
    }

    public TLBytes getNewSalt() {
        return this.newSalt;
    }

    public void setNewSalt(TLBytes newSalt) {
        this.newSalt = newSalt;
    }

    public String getEmailUnconfirmedPattern() {
        return this.emailUnconfirmedPattern;
    }

    public void setEmailUnconfirmedPattern(String emailUnconfirmedPattern) {
        this.emailUnconfirmedPattern = emailUnconfirmedPattern;
    }
}

