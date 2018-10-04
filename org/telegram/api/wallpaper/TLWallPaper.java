
package org.telegram.api.wallpaper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.api.wallpaper.TLAbsWallPaper;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLWallPaper
extends TLAbsWallPaper {
    public static final int CLASS_ID = -860866985;
    private TLVector<TLAbsPhotoSize> sizes;

    @Override
    public int getClassId() {
        return -860866985;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLVector(this.sizes, stream);
        StreamingUtils.writeInt(this.color, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.sizes = StreamingUtils.readTLVector(stream, context);
        this.color = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "wallPaper#ccb03657";
    }
}

