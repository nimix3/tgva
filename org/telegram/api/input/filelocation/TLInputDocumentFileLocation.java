
package org.telegram.api.input.filelocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputDocumentFileLocation
extends TLAbsInputFileLocation {
    public static final int CLASS_ID = 1313188841;
    private long id;
    private long accessHash;

    @Override
    public int getClassId() {
        return 1313188841;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
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
        return "inputDocumentFileLocation#4e45abe9";
    }
}

