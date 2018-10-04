
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.messages.filter.TLAbsMessagesFilter;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSearch
extends TLMethod<TLAbsMessages> {
    public static final int CLASS_ID = -732523960;
    private static final int FLAG_IMPORTANT_ONLY = 1;
    private int flags;
    private TLAbsInputPeer peer;
    private String q;
    private TLAbsMessagesFilter filter;
    private int minDate;
    private int maxDate;
    private int offset;
    private int maxId;
    private int limit;

    @Override
    public int getClassId() {
        return -732523960;
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
        throw new IOException("Incorrect response type. Expected org.telegram.api.messages.TLAbsMessages, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    public String getQ() {
        return this.q;
    }

    public void setQ(String value) {
        this.q = value;
    }

    public TLAbsMessagesFilter getFilter() {
        return this.filter;
    }

    public void setFilter(TLAbsMessagesFilter value) {
        this.filter = value;
    }

    public int getMinDate() {
        return this.minDate;
    }

    public void setMinDate(int value) {
        this.minDate = value;
    }

    public int getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(int value) {
        this.maxDate = value;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int value) {
        this.maxId = value;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLString(this.q, stream);
        StreamingUtils.writeTLObject(this.filter, stream);
        StreamingUtils.writeInt(this.minDate, stream);
        StreamingUtils.writeInt(this.maxDate, stream);
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.maxId, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.q = StreamingUtils.readTLString(stream);
        this.filter = (TLAbsMessagesFilter)StreamingUtils.readTLObject(stream, context);
        this.minDate = StreamingUtils.readInt(stream);
        this.maxDate = StreamingUtils.readInt(stream);
        this.offset = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.search#d4569248";
    }
}

