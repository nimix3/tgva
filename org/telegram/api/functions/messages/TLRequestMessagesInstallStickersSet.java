
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.sticker.set.TLAbsInputStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesInstallStickersSet
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 2066793382;
    private TLAbsInputStickerSet stickerSet;
    private boolean disabled;

    @Override
    public int getClassId() {
        return 2066793382;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.TLBool, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.stickerSet, stream);
        StreamingUtils.writeTLBool(this.disabled, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.stickerSet = (TLAbsInputStickerSet)StreamingUtils.readTLObject(stream, context);
        this.disabled = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "stickers.installStickersSet#7b30c3a6";
    }
}

