
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLMessageGroup;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChannelGroup
extends TLAbsUpdate
implements TLChannelUpdate {
    public static final int CLASS_ID = -1016324548;
    private int channelId;
    private TLMessageGroup group;

    @Override
    public int getClassId() {
        return -1016324548;
    }

    @Override
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public TLMessageGroup getGroup() {
        return this.group;
    }

    public void setGroup(TLMessageGroup group) {
        this.group = group;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.channelId, stream);
        StreamingUtils.writeTLObject(this.group, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channelId = StreamingUtils.readInt(stream);
        this.group = (TLMessageGroup)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "update.TLUpdateChannelGroup#c36c1e3c";
    }
}

