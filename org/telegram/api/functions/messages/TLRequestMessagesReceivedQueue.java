
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesReceivedQueue
extends TLMethod<TLLongVector> {
    public static final int CLASS_ID = 1436924774;
    private int maxQts;

    @Override
    public int getClassId() {
        return 1436924774;
    }

    @Override
    public TLLongVector deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLLongVector(stream, context);
    }

    public int getMaxQts() {
        return this.maxQts;
    }

    public void setMaxQts(int value) {
        this.maxQts = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.maxQts, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.maxQts = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.receivedQueue#55a5bb66";
    }
}

