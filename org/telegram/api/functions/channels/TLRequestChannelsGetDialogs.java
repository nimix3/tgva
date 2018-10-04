
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dialogs.TLAbsDialogs;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsGetDialogs
extends TLMethod<TLAbsDialogs> {
    public static final int CLASS_ID = -1445735863;
    private int offset;
    private int limit;

    @Override
    public int getClassId() {
        return -1445735863;
    }

    @Override
    public TLAbsDialogs deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsDialogs) {
            return (TLAbsDialogs)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsDialogs.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsGetDialogs#a9d3d249";
    }
}

