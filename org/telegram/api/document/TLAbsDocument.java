
package org.telegram.api.document;

import org.telegram.tl.TLObject;

public abstract class TLAbsDocument
extends TLObject {
    protected long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

