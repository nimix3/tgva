
package org.telegram.api.message;

import org.telegram.tl.TLObject;

public abstract class TLAbsMessage
extends TLObject {
    protected TLAbsMessage() {
    }

    public abstract int getChatId();
}

