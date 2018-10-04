
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesForwardMessages
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = 1888354709;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_UNUSED2 = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_BROADCAST = 16;
    private static final int FLAG_SILENT = 32;
    private static final int FLAG_BACKGROUND = 64;
    private int flags;
    private TLAbsInputPeer fromPeer;
    private TLIntVector id;
    private TLLongVector randomId;
    private TLAbsInputPeer toPeer;

    @Override
    public int getClassId() {
        return 1888354709;
    }

    @Override
    public TLAbsUpdates deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdates) {
            return (TLAbsUpdates)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLAbsInputPeer getFromPeer() {
        return this.fromPeer;
    }

    public void setFromPeer(TLAbsInputPeer fromPeer) {
        this.fromPeer = fromPeer;
    }

    public TLLongVector getRandomId() {
        return this.randomId;
    }

    public void setRandomId(TLLongVector randomId) {
        this.randomId = randomId;
    }

    public TLAbsInputPeer getToPeer() {
        return this.toPeer;
    }

    public void setToPeer(TLAbsInputPeer toPeer) {
        this.toPeer = toPeer;
    }

    public TLIntVector getId() {
        return this.id;
    }

    public void setId(TLIntVector value) {
        this.id = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.fromPeer, stream);
        StreamingUtils.writeTLVector(this.id, stream);
        StreamingUtils.writeTLVector(this.randomId, stream);
        StreamingUtils.writeTLObject(this.toPeer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.fromPeer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readTLIntVector(stream, context);
        this.randomId = StreamingUtils.readTLLongVector(stream, context);
        this.toPeer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.forwardMessages#708e0195";
    }
}

