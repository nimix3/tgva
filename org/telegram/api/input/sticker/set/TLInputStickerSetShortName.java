
package org.telegram.api.input.sticker.set;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.sticker.set.TLAbsInputStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputStickerSetShortName
extends TLAbsInputStickerSet {
    public static final int CLASS_ID = -2044933984;
    private String shortName;

    @Override
    public int getClassId() {
        return -2044933984;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.shortName, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.shortName = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "sticker.set.inputStickerSetShortName#861cc8a0";
    }
}

