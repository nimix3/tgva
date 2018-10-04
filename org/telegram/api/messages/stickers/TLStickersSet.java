
package org.telegram.api.messages.stickers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLDocument;
import org.telegram.api.messages.stickers.TLAbsStickers;
import org.telegram.api.sticker.pack.TLStickerPack;
import org.telegram.api.sticker.set.TLAbsStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLStickersSet
extends TLAbsStickers {
    public static final int CLASS_ID = -1240849242;
    private TLAbsStickerSet set;
    private TLVector<TLStickerPack> packs;
    private TLVector<TLDocument> documents;

    @Override
    public int getClassId() {
        return -1240849242;
    }

    public TLVector<TLStickerPack> getPacks() {
        return this.packs;
    }

    public void setPacks(TLVector<TLStickerPack> packs) {
        this.packs = packs;
    }

    public TLAbsStickerSet getSet() {
        return this.set;
    }

    public void setSet(TLAbsStickerSet set) {
        this.set = set;
    }

    public TLVector<TLDocument> getDocuments() {
        return this.documents;
    }

    public void setDocuments(TLVector<TLDocument> documents) {
        this.documents = documents;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.set, stream);
        StreamingUtils.writeTLVector(this.packs, stream);
        StreamingUtils.writeTLVector(this.documents, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.set = (TLAbsStickerSet)StreamingUtils.readTLObject(stream, context);
        this.packs = StreamingUtils.readTLVector(stream, context);
        this.documents = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "stickersSet#b60a24a6";
    }
}

