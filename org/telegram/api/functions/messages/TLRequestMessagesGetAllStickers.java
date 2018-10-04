
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.stickers.TLAbsAllStickers;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetAllStickers
extends TLMethod<TLAbsAllStickers> {
    public static final int CLASS_ID = 479598769;
    protected int hash;

    @Override
    public int getClassId() {
        return 479598769;
    }

    @Override
    public TLAbsAllStickers deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsAllStickers) {
            return (TLAbsAllStickers)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsAllStickers.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getHash() {
        return this.hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
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
        return "messages.getAllStickers#1c9618b1";
    }
}

