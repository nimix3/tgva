
package org.telegram.api.message.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageMediaPhoto
extends TLAbsMessageMedia {
    public static final int CLASS_ID = 1032643901;
    private TLAbsPhoto photo;
    private String caption;

    @Override
    public int getClassId() {
        return 1032643901;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsPhoto photo) {
        this.photo = photo;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messageMediaPhoto#3d8ce53d";
    }
}

