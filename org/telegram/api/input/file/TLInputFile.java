
package org.telegram.api.input.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputFile
extends TLAbsInputFile {
    public static final int CLASS_ID = -181407105;
    private String md5Checksum = "";

    @Override
    public int getClassId() {
        return -181407105;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeInt(this.parts, stream);
        StreamingUtils.writeTLString(this.name, stream);
        StreamingUtils.writeTLString(this.md5Checksum, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.parts = StreamingUtils.readInt(stream);
        this.name = StreamingUtils.readTLString(stream);
        this.md5Checksum = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputFile#f52ff27f";
    }
}

