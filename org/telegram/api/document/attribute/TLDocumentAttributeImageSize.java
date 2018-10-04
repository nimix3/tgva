
package org.telegram.api.document.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDocumentAttributeImageSize
extends TLAbsDocumentAttribute {
    public static final int CLASS_ID = 1815593308;
    private int w;
    private int h;

    @Override
    public int getClassId() {
        return 1815593308;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.w, stream);
        StreamingUtils.writeInt(this.h, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.w = StreamingUtils.readInt(stream);
        this.h = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "documentAttributeImageSize#6c37c15c";
    }
}

