
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.TLAffectedMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesReadContents
extends TLMethod<TLAffectedMessages> {
    public static final int CLASS_ID = 916930423;
    private TLIntVector id;

    @Override
    public TLAffectedMessages deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAffectedMessages) {
            return (TLAffectedMessages)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.messages.TLAffectedMessages, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public int getClassId() {
        return 916930423;
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
        return "messages.readMessageContents#36a73f77";
    }
}

