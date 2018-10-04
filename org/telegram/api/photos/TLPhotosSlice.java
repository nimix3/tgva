
package org.telegram.api.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photos.TLAbsPhotos;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLPhotosSlice
extends TLAbsPhotos {
    public static final int CLASS_ID = 352657236;
    protected int count;

    @Override
    public int getClassId() {
        return 352657236;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.photos, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.count = StreamingUtils.readInt(stream);
        this.photos = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "photos.photosSlice#15051f54";
    }
}

