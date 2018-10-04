
package org.telegram.api.decryptedmessage;

import org.telegram.tl.TLObject;

public abstract class TLAbsDecryptedMessage
extends TLObject {
    protected long randomId;

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long value) {
        this.randomId = value;
    }
}

