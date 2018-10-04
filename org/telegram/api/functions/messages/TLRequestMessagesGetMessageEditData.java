
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.TLMessagesEditData;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetMessageEditData
extends TLMethod<TLMessagesEditData> {
    public static final int CLASS_ID = -39416522;
    private TLAbsInputPeer peer;
    private int id;

    @Override
    public int getClassId() {
        return -39416522;
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer peer) {
        this.peer = peer;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public TLMessagesEditData deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLMessagesEditData) {
            return (TLMessagesEditData)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLMessagesEditData.class.getName() + ", got: " + res.getClass().getName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getMessageEditData#fda68d36";
    }
}

