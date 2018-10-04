
package org.telegram.api.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLPhotosPhoto
extends TLObject {
    public static final int CLASS_ID = 539045032;
    protected TLAbsPhoto photo;
    protected TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return 539045032;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "photos.photos#20212ca8";
    }
}

