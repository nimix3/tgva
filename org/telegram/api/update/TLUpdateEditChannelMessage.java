
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateEditChannelMessage
extends TLAbsUpdate
implements TLChannelUpdate {
    public static final int CLASS_ID = 457133559;
    private int pts;
    private int ptsCount;
    private TLAbsMessage message;

    @Override
    public int getClassId() {
        return 457133559;
    }

    public TLAbsMessage getMessage() {
        return this.message;
    }

    public void setMessage(TLAbsMessage value) {
        this.message = value;
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
    public int getChannelId() {
        return this.message.getChatId();
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.message, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.message = (TLAbsMessage)StreamingUtils.readTLObject(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateEditChannelMessage#1b3f4df7";
    }
}

