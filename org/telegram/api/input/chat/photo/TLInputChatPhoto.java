
package org.telegram.api.input.chat.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.photo.TLAbsInputChatPhoto;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputChatPhoto
extends TLAbsInputChatPhoto {
    public static final int CLASS_ID = -1293828344;
    private TLAbsInputPhoto id;
    private TLAbsInputPhotoCrop crop;

    @Override
    public int getClassId() {
        return -1293828344;
    }

    public TLAbsInputPhoto getId() {
        return this.id;
    }

    public void setId(TLAbsInputPhoto id) {
        this.id = id;
    }

    public TLAbsInputPhotoCrop getCrop() {
        return this.crop;
    }

    public void setCrop(TLAbsInputPhotoCrop crop) {
        this.crop = crop;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
        StreamingUtils.writeTLObject(this.crop, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = (TLAbsInputPhoto)StreamingUtils.readTLObject(stream, context);
        this.crop = (TLAbsInputPhotoCrop)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputChatPhoto#b2e1bf08";
    }
}

