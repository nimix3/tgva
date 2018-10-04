
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLChannelUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChannelMessageViews
extends TLAbsUpdate
implements TLChannelUpdate {
    public static final int CLASS_ID = -1734268085;
    private int channelId;
    private int id;
    private int views;

    @Override
    public int getClassId() {
        return -1734268085;
    }

    @Override
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.channelId, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeInt(this.views, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channelId = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.views = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "update.TLUpdateChannelMessageViews#98a12b4b";
    }
}

