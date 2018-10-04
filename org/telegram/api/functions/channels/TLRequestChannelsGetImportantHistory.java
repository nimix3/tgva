
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsGetImportantHistory
extends TLMethod<TLAbsMessages> {
    public static final int CLASS_ID = -1891021902;
    private TLAbsInputChannel channel;
    private int offsetId;
    private int offsetDate;
    private int addOffset;
    private int limit;
    private int maxId;
    private int minId;

    @Override
    public int getClassId() {
        return -1891021902;
    }

    @Override
    public TLAbsMessages deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsMessages) {
            return (TLAbsMessages)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsMessages.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public TLAbsInputChannel getChannel() {
        return this.channel;
    }

    public void setChannel(TLAbsInputChannel channel) {
        this.channel = channel;
    }

    public int getOffsetId() {
        return this.offsetId;
    }

    public void setOffsetId(int offsetId) {
        this.offsetId = offsetId;
    }

    public int getOffsetDate() {
        return this.offsetDate;
    }

    public void setOffsetDate(int offsetDate) {
        this.offsetDate = offsetDate;
    }

    public int getAddOffset() {
        return this.addOffset;
    }

    public void setAddOffset(int addOffset) {
        this.addOffset = addOffset;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public int getMinId() {
        return this.minId;
    }

    public void setMinId(int minId) {
        this.minId = minId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeInt(this.offsetId, stream);
        StreamingUtils.writeInt(this.offsetDate, stream);
        StreamingUtils.writeInt(this.addOffset, stream);
        StreamingUtils.writeInt(this.limit, stream);
        StreamingUtils.writeInt(this.maxId, stream);
        StreamingUtils.writeInt(this.minId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.offsetId = StreamingUtils.readInt(stream);
        this.offsetDate = StreamingUtils.readInt(stream);
        this.addOffset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
        this.minId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "channels.getImportantHistory#8f494bb2";
    }
}

