
package org.telegram.api.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLUpdatesState
extends TLObject {
    public static final int CLASS_ID = -1519637954;
    private int pts;
    private int qts;
    private int date;
    private int seq;
    private int unreadCount;

    @Override
    public int getClassId() {
        return -1519637954;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int value) {
        this.pts = value;
    }

    public int getQts() {
        return this.qts;
    }

    public void setQts(int value) {
        this.qts = value;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int value) {
        this.seq = value;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(int value) {
        this.unreadCount = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.qts, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.seq, stream);
        StreamingUtils.writeInt(this.unreadCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pts = StreamingUtils.readInt(stream);
        this.qts = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.seq = StreamingUtils.readInt(stream);
        this.unreadCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updates.state#a56c2a3e";
    }
}

