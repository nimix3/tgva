
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChannelTooLong
extends TLAbsUpdate {
    public static final int CLASS_ID = -352032773;
    private static final int FLAG_PTS = 1;
    private int flags;
    private int channelId;
    private int pts;

    @Override
    public int getClassId() {
        return -352032773;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.channelId, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.pts, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.channelId = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.pts = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "update.TLUpdateChannelTooLong#eb0467fb";
    }
}

