
package org.telegram.api.update;

import org.telegram.api.update.TLAbsUpdate;

public class TLFakeUpdate
extends TLAbsUpdate {
    private int pts;
    private int ptsCount;

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    @Override
    public int getPtsCount() {
        return this.ptsCount;
    }

    @Override
    public int getClassId() {
        return 0;
    }

    @Override
    public String toString() {
        return "fakeUpdate#0";
    }
}

