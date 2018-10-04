
package org.telegram.api.messages.dhconfig;

import org.telegram.tl.TLBytes;
import org.telegram.tl.TLObject;

public abstract class TLAbsDhConfig
extends TLObject {
    public int g;
    public TLBytes p;
    public int version;
    protected TLBytes random;

    protected TLAbsDhConfig() {
    }

    public TLBytes getRandom() {
        return this.random;
    }

    public void setRandom(TLBytes value) {
        this.random = value;
    }

    public int getG() {
        return this.g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public TLBytes getP() {
        return this.p;
    }

    public void setP(TLBytes p) {
        this.p = p;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

