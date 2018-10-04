
package org.telegram.api.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLPhoto
extends TLAbsPhoto {
    public static final int CLASS_ID = -840088834;
    private long id;
    private long accessHash;
    private int date;
    private TLVector<TLAbsPhotoSize> sizes;

    @Override
    public int getClassId() {
        return -840088834;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public TLVector<TLAbsPhotoSize> getSizes() {
        return this.sizes;
    }

    public void setSizes(TLVector<TLAbsPhotoSize> sizes) {
        this.sizes = sizes;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLObject(this.sizes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
        this.sizes = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "photos.photo#cded42fe";
    }
}

