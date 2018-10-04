
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.stickers.TLStickers;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetStickers
extends TLMethod<TLStickers> {
    public static final int CLASS_ID = -1373446075;
    private String emoticon;
    private String hash;

    @Override
    public int getClassId() {
        return -1373446075;
    }

    @Override
    public TLStickers deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLStickers) {
            return (TLStickers)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.messages.stickers.TLStickers, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.emoticon, stream);
        StreamingUtils.writeTLString(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.emoticon = StreamingUtils.readTLString(stream);
        this.hash = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "stickers.get#ae22e045";
    }
}

