
package ir.telegramp;

import java.util.Comparator;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.TLObject;

public class UpdateWrapper {
    private TLObject update;
    private boolean isChannel;
    private int pts;
    private int ptsCount;
    private int date;
    private int seq;
    private int seqStart;
    private int channelId;
    private boolean checkPts;
    private boolean updatePts;

    public UpdateWrapper(TLObject update) {
        this.update = update;
        if (update instanceof TLChannelUpdate) {
            this.isChannel = true;
            this.channelId = ((TLChannelUpdate)((Object)update)).getChannelId();
        } else {
            this.channelId = 0;
        }
        this.checkPts = true;
        this.updatePts = true;
    }

    public void setParams(int pts, int ptsCount, int date, int seq, int seqStart) {
        this.pts = pts;
        this.ptsCount = ptsCount;
        this.date = date;
        this.seq = seq;
        this.seqStart = seqStart;
    }

    public void disablePtsCheck() {
        this.checkPts = false;
    }

    public void disableUpdatePts() {
        this.updatePts = false;
    }

    public TLObject getUpdate() {
        return this.update;
    }

    public int getPts() {
        return this.pts;
    }

    public int getPtsCount() {
        return this.ptsCount;
    }

    public int getDate() {
        return this.date;
    }

    public int getSeq() {
        return this.seq;
    }

    public int getSeqStart() {
        return this.seqStart;
    }

    public boolean isCheckPts() {
        return this.checkPts;
    }

    public boolean isUpdatePts() {
        return this.updatePts;
    }

    public boolean isChannel() {
        return this.isChannel;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public String toString() {
        return this.update == null ? null : this.update.toString();
    }

    static class UpdateWrapperComparator
    implements Comparator<UpdateWrapper> {
        UpdateWrapperComparator() {
        }

        @Override
        public int compare(UpdateWrapper o1, UpdateWrapper o2) {
            int result = Integer.compare(o1.getPts(), o2.getPts());
            if (result == 0) {
                result = Integer.compare(o2.getPtsCount(), o1.getPtsCount());
            }
            return result;
        }
    }

}

