
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetMessagesViews
extends TLMethod<TLIntVector> {
    public static final int CLASS_ID = -993483427;
    private TLAbsInputPeer peer;
    private TLIntVector id;
    private boolean increment;

    @Override
    public int getClassId() {
        return -993483427;
    }

    @Override
    public TLIntVector deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLIntVector) {
            return (TLIntVector)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLIntVector.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLIntVector getId() {
        return this.id;
    }

    public void setId(TLIntVector value) {
        this.id = value;
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer peer) {
        this.peer = peer;
    }

    public boolean isIncrement() {
        return this.increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLVector(this.id, stream);
        StreamingUtils.writeTLBool(this.increment, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readTLIntVector(stream, context);
        this.increment = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "messages.getMessagesViews#c4c8a55d";
    }
}

