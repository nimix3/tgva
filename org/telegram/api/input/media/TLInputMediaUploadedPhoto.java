
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaUploadedPhoto
extends TLAbsInputMedia {
    public static final int CLASS_ID = -139464256;
    private TLAbsInputFile file;
    private String caption;

    @Override
    public int getClassId() {
        return -139464256;
    }

    public TLAbsInputFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsInputFile value) {
        this.file = value;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.file, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.file = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaUploadedPhoto#f7aff1c0";
    }
}

