
package org.telegram.api.messages.stickers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.stickers.TLAbsAllStickers;
import org.telegram.api.sticker.set.TLAbsStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLAllStickers
extends TLAbsAllStickers {
    public static final int CLASS_ID = -302170017;
    private int hash;
    private TLVector<TLAbsStickerSet> sets = new TLVector();

    @Override
    public int getClassId() {
        return -302170017;
    }

    public int getHash() {
        return this.hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public TLVector<TLAbsStickerSet> getSets() {
        return this.sets;
    }

    public void setSets(TLVector<TLAbsStickerSet> sets) {
        this.sets = sets;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.hash, stream);
        StreamingUtils.writeTLVector(this.sets, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readInt(stream);
        this.sets = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "allStickers#edfd405f";
    }
}

