
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSearchGlobal
extends TLMethod<TLAbsMessages> {
    public static final int CLASS_ID = -1640190800;
    private String q;
    private int offsetDate;
    private TLAbsInputPeer offsetPeer;
    private int offsetId;
    private int limit;

    @Override
    public int getClassId() {
        return -1640190800;
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
        throw new IOException("Incorrect response type. Expected " + TLAbsMessages.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public String getQ() {
        return this.q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getOffsetDate() {
        return this.offsetDate;
    }

    public void setOffsetDate(int offsetDate) {
        this.offsetDate = offsetDate;
    }

    public TLAbsInputPeer getOffsetPeer() {
        return this.offsetPeer;
    }

    public void setOffsetPeer(TLAbsInputPeer offsetPeer) {
        this.offsetPeer = offsetPeer;
    }

    public int getOffsetId() {
        return this.offsetId;
    }

    public void setOffsetId(int offsetId) {
        this.offsetId = offsetId;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.q, stream);
        StreamingUtils.writeInt(this.offsetDate, stream);
        StreamingUtils.writeTLObject(this.offsetPeer, stream);
        StreamingUtils.writeInt(this.offsetId, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.q = StreamingUtils.readTLString(stream);
        this.offsetDate = StreamingUtils.readInt(stream);
        this.offsetPeer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.offsetId = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.searchGlobal#9e3cacb0";
    }
}

