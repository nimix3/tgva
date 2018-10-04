
package org.telegram.api.update;

import org.telegram.tl.TLObject;

public abstract class TLAbsUpdate
extends TLObject {
    protected TLAbsUpdate() {
    }

    public int getPts() {
        return 0;
    }

    public int getPtsCount() {
        return 0;
    }
}

