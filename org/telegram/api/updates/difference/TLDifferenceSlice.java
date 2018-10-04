
package org.telegram.api.updates.difference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.TLUpdatesState;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDifferenceSlice
extends TLAbsDifference {
    public static final int CLASS_ID = -1459938943;
    private TLUpdatesState intermediateState;

    @Override
    public int getClassId() {
        return -1459938943;
    }

    public TLUpdatesState getIntermediateState() {
        return this.intermediateState;
    }

    public void setIntermediateState(TLUpdatesState intermediateState) {
        this.intermediateState = intermediateState;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.newMessages, stream);
        StreamingUtils.writeTLVector(this.newEncryptedMessages, stream);
        StreamingUtils.writeTLVector(this.otherUpdates, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
        StreamingUtils.writeTLObject(this.intermediateState, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.newMessages = StreamingUtils.readTLVector(stream, context);
        this.newEncryptedMessages = StreamingUtils.readTLVector(stream, context);
        this.otherUpdates = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
        this.intermediateState = (TLUpdatesState)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updates.differenceSlice#a8fb1981";
    }
}

