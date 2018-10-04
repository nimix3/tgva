
package org.telegram.api.input.encrypted.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.encrypted.file.TLAbsInputEncryptedFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputEncryptedFileUploaded
extends TLAbsInputEncryptedFile {
    public static final int CLASS_ID = 1690108678;

    @Override
    public int getClassId() {
        return 1690108678;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeInt(this.parts, stream);
        StreamingUtils.writeTLString(this.md5Checksum, stream);
        StreamingUtils.writeInt(this.keyFingerprint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.parts = StreamingUtils.readInt(stream);
        this.md5Checksum = StreamingUtils.readTLString(stream);
        this.keyFingerprint = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "inputEncryptedFileUploaded#64bd0306";
    }
}

