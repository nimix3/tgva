
package org.telegram.api.updates.channel.differences;

import org.telegram.tl.TLObject;

public abstract class TLAbsUpdatesChannelDifferences
extends TLObject {
    protected int flags;
    protected int pts;
    protected int timeout;

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

