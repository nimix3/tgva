
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateReadMessagesInbox
extends TLAbsUpdate {
    public static final int CLASS_ID = -1721631396;
    private TLAbsPeer peer;
    private int maxId;
    private int pts;
    private int ptsCount;

    @Override
    public int getClassId() {
        return -1721631396;
    }

    public TLAbsPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsPeer peer) {
        this.peer = peer;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    @Override
    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.maxId, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        this.maxId = StreamingUtils.readInt(stream);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateReadMessagesInbox#9961fd5c";
    }
}

