
package org.telegram.api.photo.size;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLPhotoSizeEmpty
extends TLAbsPhotoSize {
    public static final int CLASS_ID = 236446268;
    private String type;

    @Override
    public int getClassId() {
        return 236446268;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.type, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.type = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "photoSizeEmpty#e17e23c";
    }
}

