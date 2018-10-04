
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaPhoto
extends TLAbsInputMedia {
    public static final int CLASS_ID = -373312269;
    private TLAbsInputPhoto id;
    private String caption;

    @Override
    public int getClassId() {
        return -373312269;
    }

    public TLAbsInputPhoto getId() {
        return this.id;
    }

    public void setId(TLAbsInputPhoto value) {
        this.id = value;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = (TLAbsInputPhoto)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaPhoto#e9bfb4f3";
    }
}

