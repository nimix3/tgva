
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.dialogs.TLAbsDialogs;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetDialogs
extends TLMethod<TLAbsDialogs> {
    public static final int CLASS_ID = 1799878989;
    private int offsetDate;
    private int offsetId;
    private TLAbsInputPeer offsetPeer;
    private int limit;

    @Override
    public int getClassId() {
        return 1799878989;
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

    public int getOffsetDate() {
        return this.offsetDate;
    }

    public void setOffsetDate(int offsetDate) {
        this.offsetDate = offsetDate;
    }

    public int getOffsetId() {
        return this.offsetId;
    }

    public void setOffsetId(int offsetId) {
        this.offsetId = offsetId;
    }

    public TLAbsInputPeer getOffsetPeer() {
        return this.offsetPeer;
    }

    public void setOffsetPeer(TLAbsInputPeer offsetPeer) {
        this.offsetPeer = offsetPeer;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offsetDate, stream);
        StreamingUtils.writeInt(this.offsetId, stream);
        StreamingUtils.writeTLObject(this.offsetPeer, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offsetDate = StreamingUtils.readInt(stream);
        this.offsetId = StreamingUtils.readInt(stream);
        this.offsetPeer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getDialogs#6b47f94d";
    }
}

