
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.savedgifs.TLAbsSavedGifs;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetSavedGifs
extends TLMethod<TLAbsSavedGifs> {
    public static final int CLASS_ID = -2084618926;
    private int hash;

    @Override
    public int getClassId() {
        return -2084618926;
    }

    @Override
    public TLAbsSavedGifs deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsSavedGifs) {
            return (TLAbsSavedGifs)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsSavedGifs.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getSavedGifs#83bf3d52";
    }
}

