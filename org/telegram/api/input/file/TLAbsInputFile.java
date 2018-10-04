
package org.telegram.api.input.file;

import org.telegram.tl.TLObject;

public abstract class TLAbsInputFile
extends TLObject {
    protected long id;
    protected int parts;
    protected String name;

    protected TLAbsInputFile() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public int getParts() {
        return this.parts;
    }

    public void setParts(int value) {
        this.parts = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }
}

