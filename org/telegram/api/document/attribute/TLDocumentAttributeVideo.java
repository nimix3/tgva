
package org.telegram.api.document.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDocumentAttributeVideo
extends TLAbsDocumentAttribute {
    public static final int CLASS_ID = 1494273227;
    private int duration;
    private int w;
    private int h;

    @Override
    public int getClassId() {
        return 1494273227;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        StreamingUtils.writeInt(this.duration, stream);
        StreamingUtils.writeInt(this.w, stream);
        StreamingUtils.writeInt(this.h, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.duration = StreamingUtils.readInt(stream);
        this.w = StreamingUtils.readInt(stream);
        this.h = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "documentAttributeVideo#5910cccb";
    }
}

