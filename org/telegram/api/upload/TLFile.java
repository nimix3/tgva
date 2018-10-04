
package org.telegram.api.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.storage.file.TLAbsFileType;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLFile
extends TLObject {
    public static final int CLASS_ID = 157948117;
    private TLAbsFileType type;
    private int mtime;
    private TLBytes bytes;

    @Override
    public int getClassId() {
        return 157948117;
    }

    public TLAbsFileType getType() {
        return this.type;
    }

    public void setType(TLAbsFileType value) {
        this.type = value;
    }

    public int getMtime() {
        return this.mtime;
    }

    public void setMtime(int value) {
        this.mtime = value;
    }

    public TLBytes getBytes() {
        return this.bytes;
    }

    public void setBytes(TLBytes value) {
        this.bytes = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.type, stream);
        StreamingUtils.writeInt(this.mtime, stream);
        StreamingUtils.writeTLBytes(this.bytes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.type = (TLAbsFileType)StreamingUtils.readTLObject(stream, context);
        this.mtime = StreamingUtils.readInt(stream);
        this.bytes = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "upload.file#96a18d5";
    }
}

