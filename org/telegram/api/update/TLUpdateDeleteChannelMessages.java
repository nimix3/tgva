
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;

public class TLUpdateDeleteChannelMessages
extends TLAbsUpdate
implements TLChannelUpdate {
    public static final int CLASS_ID = -1015733815;
    private int channelId;
    private TLIntVector messages;
    private int pts;
    private int ptsCount;

    @Override
    public int getClassId() {
        return -1015733815;
    }

    @Override
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public TLIntVector getMessages() {
        return this.messages;
    }

    public void setMessages(TLIntVector messages) {
        this.messages = messages;
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
        StreamingUtils.writeInt(this.channelId, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channelId = StreamingUtils.readInt(stream);
        this.messages = StreamingUtils.readTLIntVector(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "update.TLUpdateDeleteChannelMessages#c37521c9";
    }
}

