
package org.telegram.api.chat.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.photo.TLAbsChatPhoto;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatPhoto
extends TLAbsChatPhoto {
    public static final int CLASS_ID = 1632839530;
    private TLAbsFileLocation photo_small;
    private TLAbsFileLocation photo_big;

    @Override
    public int getClassId() {
        return 1632839530;
    }

    public TLAbsFileLocation getPhoto_small() {
        return this.photo_small;
    }

    public void setPhoto_small(TLAbsFileLocation photo_small) {
        this.photo_small = photo_small;
    }

    public TLAbsFileLocation getPhoto_big() {
        return this.photo_big;
    }

    public void setPhoto_big(TLAbsFileLocation photo_big) {
        this.photo_big = photo_big;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.photo_small, stream);
        StreamingUtils.writeTLObject(this.photo_big, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.photo_small = (TLAbsFileLocation)StreamingUtils.readTLObject(stream, context);
        this.photo_big = (TLAbsFileLocation)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "chatPhoto#6153276a";
    }
}

