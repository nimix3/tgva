
package org.telegram.api.updates.difference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.TLUpdatesState;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDifference
extends TLAbsDifference {
    public static final int CLASS_ID = 16030880;
    private TLUpdatesState state;

    @Override
    public int getClassId() {
        return 16030880;
    }

    public TLUpdatesState getState() {
        return this.state;
    }

    public void setState(TLUpdatesState state) {
        this.state = state;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.newMessages, stream);
        StreamingUtils.writeTLVector(this.newEncryptedMessages, stream);
        StreamingUtils.writeTLVector(this.otherUpdates, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
        StreamingUtils.writeTLObject(this.state, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.newMessages = StreamingUtils.readTLVector(stream, context);
        this.newEncryptedMessages = StreamingUtils.readTLVector(stream, context);
        this.otherUpdates = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
        this.state = (TLUpdatesState)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updates.difference#f49ca0";
    }
}

