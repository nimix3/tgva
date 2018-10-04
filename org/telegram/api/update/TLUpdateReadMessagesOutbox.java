
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateReadMessagesOutbox
extends TLAbsUpdate {
    public static final int CLASS_ID = 791617983;
    private int pts;
    private int maxId;
    private int ptsCount;
    private TLAbsPeer peer;

    @Override
    public int getClassId() {
        return 791617983;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    @Override
    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    public TLAbsPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsPeer peer) {
        this.peer = peer;
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
        return "updateReadMessageOutbox#2f2f21bf";
    }
}

