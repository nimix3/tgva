
package org.telegram.api.input.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputFileBig
extends TLAbsInputFile {
    public static final int CLASS_ID = -95482955;

    @Override
    public int getClassId() {
        return -95482955;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeInt(this.parts, stream);
        StreamingUtils.writeTLString(this.name, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.parts = StreamingUtils.readInt(stream);
        this.name = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputFileBig#fa4f0bb5";
    }
}

