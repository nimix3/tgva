
package org.telegram.api.channel.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.filters.TLAbsChannelMessagesFilter;
import org.telegram.api.message.TLMessageRange;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLChannelMessagesFilter
extends TLAbsChannelMessagesFilter {
    public static final int CLASS_ID = -847783593;
    public static final int FLAG_IMPORTANT_ONLY = 1;
    public static final int FLAG_EXCLUDE_NEW_MESSAGES = 2;
    private int flags;
    private TLVector<TLMessageRange> ranges;

    @Override
    public int getClassId() {
        return -847783593;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLVector<TLMessageRange> getRanges() {
        return this.ranges;
    }

    public void setRanges(TLVector<TLMessageRange> ranges) {
        this.ranges = ranges;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLVector(this.ranges, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.ranges = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "channel.messages.filter.TLChannelMessagesFilter#cd77d957";
    }
}

