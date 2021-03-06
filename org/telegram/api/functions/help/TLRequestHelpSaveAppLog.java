
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.TLInputAppEvent;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestHelpSaveAppLog
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 1862465352;
    private TLVector<TLInputAppEvent> events;

    @Override
    public int getClassId() {
        return 1862465352;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    public TLVector<TLInputAppEvent> getEvents() {
        return this.events;
    }

    public void setEvents(TLVector<TLInputAppEvent> value) {
        this.events = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.events, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.events = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "help.saveAppLog#6f02f748";
    }
}

