
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.TLAffectedHistory;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesDeleteHistory
extends TLMethod<TLAffectedHistory> {
    public static final int CLASS_ID = -1212072999;
    private TLAbsInputPeer peer;
    private int maxId;

    @Override
    public int getClassId() {
        return -1212072999;
    }

    @Override
    public TLAffectedHistory deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAffectedHistory) {
            return (TLAffectedHistory)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAffectedHistory.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.maxId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.maxId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.deleteHistory#b7c13bd9";
    }
}

