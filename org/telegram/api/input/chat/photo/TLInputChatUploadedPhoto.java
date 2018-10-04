
package org.telegram.api.input.chat.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.photo.TLAbsInputChatPhoto;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputChatUploadedPhoto
extends TLAbsInputChatPhoto {
    public static final int CLASS_ID = -1809496270;
    protected TLAbsInputPhotoCrop crop;
    protected TLAbsInputFile file;

    @Override
    public int getClassId() {
        return -1809496270;
    }

    public TLAbsInputPhotoCrop getCrop() {
        return this.crop;
    }

    public void setCrop(TLAbsInputPhotoCrop crop) {
        this.crop = crop;
    }

    public TLAbsInputFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsInputFile file) {
        this.file = file;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.file, stream);
        StreamingUtils.writeTLObject(this.crop, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.file = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.crop = (TLAbsInputPhotoCrop)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputChatUploadedPhoto#94254732";
    }
}

