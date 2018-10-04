
package org.telegram.api.message.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageMediaDocument
extends TLAbsMessageMedia {
    public static final int CLASS_ID = -203411800;
    private TLAbsDocument document;
    private String caption;

    @Override
    public int getClassId() {
        return -203411800;
    }

    public TLAbsDocument getDocument() {
        return this.document;
    }

    public void setDocument(TLAbsDocument document) {
        this.document = document;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.document, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.document = (TLAbsDocument)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messageMediaDocument#f3e02ea8";
    }
}

