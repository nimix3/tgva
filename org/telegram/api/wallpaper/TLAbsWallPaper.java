
package org.telegram.api.wallpaper;

import org.telegram.tl.TLObject;

public abstract class TLAbsWallPaper
extends TLObject {
    protected int id;
    protected String title;
    protected int color;

    protected TLAbsWallPaper() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int value) {
        this.color = value;
    }
}

