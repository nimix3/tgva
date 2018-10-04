
package org.telegram.api.input.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.document.TLAbsInputDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputDocument
extends TLAbsInputDocument {
    public static final int CLASS_ID = 410618194;

    @Override
    public int getClassId() {
        return 410618194;
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
        return "inputDocument#18798952";
    }
}

