
package org.telegram.api.document.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.api.input.sticker.set.TLAbsInputStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDocumentAttributeSticker
extends TLAbsDocumentAttribute {
    public static final int CLASS_ID = 978674434;
    private String alt;
    private TLAbsInputStickerSet stickerset;

    public String getAlt() {
        return this.alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public TLAbsInputStickerSet getStickerset() {
        return this.stickerset;
    }

    public void setStickerset(TLAbsInputStickerSet stickerset) {
        this.stickerset = stickerset;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.alt, stream);
        StreamingUtils.writeTLObject(this.stickerset, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.alt = StreamingUtils.readTLString(stream);
        this.stickerset = (TLAbsInputStickerSet)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public int getClassId() {
        return 978674434;
    }

    @Override
    public String toString() {
        return "documentAttributeSticker#3a556302";
    }
}

