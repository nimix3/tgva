
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLExportedMessageLink
extends TLObject {
    public static final int CLASS_ID = 524838915;
    private String link;

    @Override
    public int getClassId() {
        return 524838915;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.link, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.link = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "message.exportedMessageLink#1f486803";
    }
}

