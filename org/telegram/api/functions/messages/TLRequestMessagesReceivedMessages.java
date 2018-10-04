
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.TLReceivedNotifyMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesReceivedMessages
extends TLMethod<TLVector<TLReceivedNotifyMessage>> {
    public static final int CLASS_ID = 94983360;
    private int maxId;

    @Override
    public int getClassId() {
        return 94983360;
    }

    @Override
    public TLVector<TLReceivedNotifyMessage> deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLVector(stream, context);
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int value) {
        this.maxId = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.maxId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.maxId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.receivedMessages#5a954c0";
    }
}

