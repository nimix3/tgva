
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.foundgif.TLAbsFoundGif;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSearchGifs
extends TLMethod<TLAbsFoundGif> {
    public static final int CLASS_ID = -1080395925;
    private String q;
    private int offset;

    @Override
    public int getClassId() {
        return -1080395925;
    }

    @Override
    public TLAbsFoundGif deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsFoundGif) {
            return (TLAbsFoundGif)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsFoundGif.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.q, stream);
        StreamingUtils.writeInt(this.offset, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.q = StreamingUtils.readTLString(stream);
        this.offset = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.searchGifs#bf9a776b";
    }
}

