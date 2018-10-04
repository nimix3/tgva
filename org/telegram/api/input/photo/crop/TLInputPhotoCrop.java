
package org.telegram.api.input.photo.crop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputPhotoCrop
extends TLAbsInputPhotoCrop {
    public static final int CLASS_ID = -644787419;
    private double cropLeft;
    private double cropTop;
    private double cropWidth;

    @Override
    public int getClassId() {
        return -644787419;
    }

    public double getCropLeft() {
        return this.cropLeft;
    }

    public void setCropLeft(double cropLeft) {
        this.cropLeft = cropLeft;
    }

    public double getCropTop() {
        return this.cropTop;
    }

    public void setCropTop(double cropTop) {
        this.cropTop = cropTop;
    }

    public double getCropWidth() {
        return this.cropWidth;
    }

    public void setCropWidth(double cropWidth) {
        this.cropWidth = cropWidth;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeDouble(this.cropLeft, stream);
        StreamingUtils.writeDouble(this.cropTop, stream);
        StreamingUtils.writeDouble(this.cropWidth, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.cropLeft = StreamingUtils.readDouble(stream);
        this.cropTop = StreamingUtils.readDouble(stream);
        this.cropWidth = StreamingUtils.readDouble(stream);
    }

    @Override
    public String toString() {
        return "inputPhotoCrop#d9915325";
    }
}

