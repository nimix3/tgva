
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.document.TLAbsInputDocument;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaDocument
extends TLAbsInputMedia {
    public static final int CLASS_ID = 444068508;
    protected TLAbsInputDocument id;
    private String caption;

    @Override
    public int getClassId() {
        return 444068508;
    }

    public TLAbsInputDocument getId() {
        return this.id;
    }

    public void setId(TLAbsInputDocument value) {
        this.id = value;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = (TLAbsInputDocument)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaDocument#1a77f29c";
    }
}

