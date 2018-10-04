
package org.telegram.api.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDocumentEmpty
extends TLAbsDocument {
    public static final int CLASS_ID = 922273905;

    @Override
    public int getClassId() {
        return 922273905;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "documentEmpty#36f8c871";
    }
}

