
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateReadChannelInbox
extends TLAbsUpdate
implements TLChannelUpdate {
    public static final int CLASS_ID = 1108669311;
    private int channelId;
    private int maxId;

    @Override
    public int getClassId() {
        return 1108669311;
    }

    @Override
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.channelId, stream);
        StreamingUtils.writeInt(this.maxId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channelId = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "update.TLUpdateReadChannelInbox#4214f37f";
    }
}

