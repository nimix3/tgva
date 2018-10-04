
package org.telegram.api.functions.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.api.photo.TLPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestPhotosUploadProfilePhoto
extends TLMethod<TLPhoto> {
    public static final int CLASS_ID = -720397176;
    private TLAbsInputFile file;
    private String caption;
    private TLAbsInputGeoPoint geoPoint;
    private TLAbsInputPhotoCrop crop;

    @Override
    public int getClassId() {
        return -720397176;
    }

    @Override
    public TLPhoto deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLPhoto) {
            return (TLPhoto)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.photo.TLPhoto, got: " + res.getClass().getCanonicalName());
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

    public void setCaption(String value) {
        this.caption = value;
    }

    public TLAbsInputGeoPoint getGeoPoint() {
        return this.geoPoint;
    }

    public void setGeoPoint(TLAbsInputGeoPoint value) {
        this.geoPoint = value;
    }

    public TLAbsInputPhotoCrop getCrop() {
        return this.crop;
    }

    public void setCrop(TLAbsInputPhotoCrop value) {
        this.crop = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.file, stream);
        StreamingUtils.writeTLString(this.caption, stream);
        StreamingUtils.writeTLObject(this.geoPoint, stream);
        StreamingUtils.writeTLObject(this.crop, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.file = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
        this.geoPoint = (TLAbsInputGeoPoint)StreamingUtils.readTLObject(stream, context);
        this.crop = (TLAbsInputPhotoCrop)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "photos.uploadProfilePhoto#d50f9c88";
    }
}

