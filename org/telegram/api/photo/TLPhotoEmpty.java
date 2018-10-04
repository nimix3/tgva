
package org.telegram.api.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLPhotoEmpty
extends TLAbsPhoto {
    public static final int CLASS_ID = 590459437;
    private long id;

    @Override
    public int getClassId() {
        return 590459437;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "photoEmpty#2331b22d";
    }
}

