
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSetBotCallbackAnswer
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 319564933;
    private static final int FLAG_MESSAGE = 1;
    private static final int FLAG_ALERT = 2;
    private int flags;
    private long queryId;
    private String message;

    @Override
    public int getClassId() {
        return 319564933;
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
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getName() + ", got: " + res.getClass().getName());
    }

    public int getFlags() {
        return this.flags;
    }

    public long getQueryId() {
        return this.queryId;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.queryId, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLString(this.message, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.queryId = StreamingUtils.readLong(stream);
        if ((this.flags & 1) != 0) {
            this.message = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "messages.setBotCallbackAnswer#481c591a";
    }
}

