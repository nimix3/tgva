
package org.telegram.api.user.profile.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUserProfilePhoto
extends TLAbsUserProfilePhoto {
    public static final int CLASS_ID = -715532088;
    private long photoId;
    private TLAbsFileLocation photoSmall;
    private TLAbsFileLocation photoBig;

    @Override
    public int getClassId() {
        return -715532088;
    }

    public long getPhotoId() {
        return this.photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public TLAbsFileLocation getPhotoSmall() {
        return this.photoSmall;
    }

    public void setPhotoSmall(TLAbsFileLocation photoSmall) {
        this.photoSmall = photoSmall;
    }

    public TLAbsFileLocation getPhotoBig() {
        return this.photoBig;
    }

    public void setPhotoBig(TLAbsFileLocation photoBig) {
        this.photoBig = photoBig;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.photoId, stream);
        StreamingUtils.writeTLObject(this.photoSmall, stream);
        StreamingUtils.writeTLObject(this.photoBig, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.photoId = StreamingUtils.readLong(stream);
        this.photoSmall = (TLAbsFileLocation)StreamingUtils.readTLObject(stream, context);
        this.photoBig = (TLAbsFileLocation)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "userProfilePhoto#d559d8c8";
    }
}

