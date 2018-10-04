
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.sticker.set.TLAbsStickerSet;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateNewStickerSet
extends TLAbsUpdate {
    public static final int CLASS_ID = 1753886890;
    private TLAbsStickerSet stickerSet;

    @Override
    public int getClassId() {
        return 1753886890;
    }

    public TLAbsStickerSet getStickerSet() {
        return this.stickerSet;
    }

    public void setStickerSet(TLAbsStickerSet stickerSet) {
        this.stickerSet = stickerSet;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.stickerSet, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.stickerSet = (TLAbsStickerSet)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateNewStickerSet#688a30aa";
    }
}

