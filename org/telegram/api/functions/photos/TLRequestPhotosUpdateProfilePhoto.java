
package org.telegram.api.functions.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestPhotosUpdateProfilePhoto
extends TLMethod<TLAbsUserProfilePhoto> {
    public static final int CLASS_ID = -285902432;
    private TLAbsInputPhoto id;
    private TLAbsInputPhotoCrop crop;

    @Override
    public int getClassId() {
        return -285902432;
    }

    @Override
    public TLAbsUserProfilePhoto deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUserProfilePhoto) {
            return (TLAbsUserProfilePhoto)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputPhoto getId() {
        return this.id;
    }

    public void setId(TLAbsInputPhoto value) {
        this.id = value;
    }

    public TLAbsInputPhotoCrop getCrop() {
        return this.crop;
    }

    public void setCrop(TLAbsInputPhotoCrop value) {
        this.crop = value;
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
        return "photos.updateProfilePhoto#eef579a0";
    }
}

