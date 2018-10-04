
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.sticker.set.TLAbsInputStickerSet;
import org.telegram.api.sticker.set.TLAbsStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetStickersSet
extends TLMethod<TLAbsStickerSet> {
    public static final int CLASS_ID = 639215886;
    private TLAbsInputStickerSet stickerSet;

    @Override
    public int getClassId() {
        return 639215886;
    }

    @Override
    public TLAbsStickerSet deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsStickerSet) {
            return (TLAbsStickerSet)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.messages.stickers.TLAbsStickerSet, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.stickerSet, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.stickerSet = (TLAbsInputStickerSet)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "stickers.getStickersSet#2619a90e";
    }
}

