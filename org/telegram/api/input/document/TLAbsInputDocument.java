
package org.telegram.api.input.document;

import org.telegram.tl.TLObject;

public abstract class TLAbsInputDocument
extends TLObject {
    protected long id;
    protected long accessHash;

    protected TLAbsInputDocument() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }
}

