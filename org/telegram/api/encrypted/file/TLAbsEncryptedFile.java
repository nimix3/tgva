
package org.telegram.api.encrypted.file;

import org.telegram.tl.TLObject;

public abstract class TLAbsEncryptedFile
extends TLObject {
    protected long id;

    protected TLAbsEncryptedFile() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

