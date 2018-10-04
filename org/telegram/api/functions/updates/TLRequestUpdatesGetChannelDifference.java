
package org.telegram.api.functions.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.filters.TLAbsChannelMessagesFilter;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.updates.channel.differences.TLAbsUpdatesChannelDifferences;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUpdatesGetChannelDifference
extends TLMethod<TLAbsUpdatesChannelDifferences> {
    public static final int CLASS_ID = -1154295872;
    private TLAbsInputChannel peer;
    private TLAbsChannelMessagesFilter filter;
    private int pts;
    private int limit;

    @Override
    public int getClassId() {
        return -1154295872;
    }

    @Override
    public TLAbsUpdatesChannelDifferences deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdatesChannelDifferences) {
            return (TLAbsUpdatesChannelDifferences)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdatesChannelDifferences.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int value) {
        this.pts = value;
    }

    public TLAbsInputChannel getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputChannel peer) {
        this.peer = peer;
    }

    public TLAbsChannelMessagesFilter getFilter() {
        return this.filter;
    }

    public void setFilter(TLAbsChannelMessagesFilter filter) {
        this.filter = filter;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLObject(this.filter, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.filter = (TLAbsChannelMessagesFilter)StreamingUtils.readTLObject(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updates.TLRequestUpdatesGetChannelDifference#bb32d7c0";
    }
}

