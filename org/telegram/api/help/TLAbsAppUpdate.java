
package org.telegram.api.help;

import org.telegram.tl.TLObject;

public abstract class TLAbsAppUpdate
extends TLObject {
    protected int id;
    protected boolean critical;
    protected String url;
    protected String text;

    protected TLAbsAppUpdate() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCritical() {
        return this.critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

