
package org.telegram.api.input.encrypted.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.encrypted.file.TLAbsInputEncryptedFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputEncryptedFile
extends TLAbsInputEncryptedFile {
    public static final int CLASS_ID = 1511503333;

    @Override
    public int getClassId() {
        return 1511503333;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "inputEncryptedFile#5a17b5e5";
    }
}

