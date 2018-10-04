
package org.telegram.api.document.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDocumentAttributeFilename
extends TLAbsDocumentAttribute {
    public static final int CLASS_ID = 358154344;
    private String fileName;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getClassId() {
        return 358154344;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.fileName, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.fileName = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "documentAttributeFilename#15590068";
    }
}

