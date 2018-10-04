
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.TLChannelParticipants;
import org.telegram.api.channel.participants.filters.TLAbsChannelParticipantsFilter;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsGetParticipants
extends TLMethod<TLChannelParticipants> {
    public static final int CLASS_ID = 618237842;
    private TLAbsInputChannel channel;
    private TLAbsChannelParticipantsFilter filter;
    private int offset;
    private int limit;

    @Override
    public int getClassId() {
        return 618237842;
    }

    @Override
    public TLChannelParticipants deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLChannelParticipants) {
            return (TLChannelParticipants)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLChannelParticipants.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLAbsInputChannel getChannel() {
        return this.channel;
    }

    public void setChannel(TLAbsInputChannel channel) {
        this.channel = channel;
    }

    public TLAbsChannelParticipantsFilter getFilter() {
        return this.filter;
    }

    public void setFilter(TLAbsChannelParticipantsFilter filter) {
        this.filter = filter;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeTLObject(this.filter, stream);
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.filter = (TLAbsChannelParticipantsFilter)StreamingUtils.readTLObject(stream, context);
        this.offset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsGetParticipants#24d98f92";
    }
}

