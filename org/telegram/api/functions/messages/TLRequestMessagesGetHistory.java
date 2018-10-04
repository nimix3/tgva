
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

public class TLRequestMessagesGetHistory
extends TLMethod<TLAbsMessages> {
    public static final int CLASS_ID = -1347868602;
    private TLAbsInputPeer peer;
    private int offsetId;
    private int offsetDate;
    private int addOffset;
    private int limit;
    private int maxId;
    private int minId;

    @Override
    public int getClassId() {
        return -1347868602;
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

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
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

    public int getMinId() {
        return this.minId;
    }

    public void setMinId(int minId) {
        this.minId = minId;
    }

    public int getAddOffset() {
        return this.addOffset;
    }

    public void setAddOffset(int addOffset) {
        this.addOffset = addOffset;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.offsetId, stream);
        StreamingUtils.writeInt(this.offsetDate, stream);
        StreamingUtils.writeInt(this.addOffset, stream);
        StreamingUtils.writeInt(this.limit, stream);
        StreamingUtils.writeInt(this.maxId, stream);
        StreamingUtils.writeInt(this.minId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.offsetId = StreamingUtils.readInt(stream);
        this.offsetDate = StreamingUtils.readInt(stream);
        this.addOffset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
        this.minId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getHistory#afa92846";
    }
}

