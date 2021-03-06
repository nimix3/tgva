
package org.telegram.api.photo.size;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLPhotoSize
extends TLAbsPhotoSize {
    public static final int CLASS_ID = 2009052699;
    private String type;
    private TLAbsFileLocation location;
    private int w;
    private int h;
    private int size;

    @Override
    public int getClassId() {
        return 2009052699;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TLAbsFileLocation getLocation() {
        return this.location;
    }

    public void setLocation(TLAbsFileLocation location) {
        this.location = location;
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

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.type, stream);
        StreamingUtils.writeTLObject(this.location, stream);
        StreamingUtils.writeInt(this.w, stream);
        StreamingUtils.writeInt(this.h, stream);
        StreamingUtils.writeInt(this.size, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.type = StreamingUtils.readTLString(stream);
        this.location = (TLAbsFileLocation)StreamingUtils.readTLObject(stream, context);
        this.w = StreamingUtils.readInt(stream);
        this.h = StreamingUtils.readInt(stream);
        this.size = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "photoSize#77bfb61b";
    }
}

