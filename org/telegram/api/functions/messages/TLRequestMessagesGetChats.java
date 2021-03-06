
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.TLMessagesChats;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetChats
extends TLMethod<TLMessagesChats> {
    public static final int CLASS_ID = 1013621127;
    private TLIntVector id;

    @Override
    public int getClassId() {
        return 1013621127;
    }

    @Override
    public TLMessagesChats deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLMessagesChats) {
            return (TLMessagesChats)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLMessagesChats.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLIntVector getId() {
        return this.id;
    }

    public void setId(TLIntVector id) {
        this.id = id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.getChats#3c6aa187";
    }
}

