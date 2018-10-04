
package org.telegram.api.webpage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.webpage.TLAbsWebPage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLWebPageEmpty
extends TLAbsWebPage {
    public static final int CLASS_ID = -350980120;
    private long id;

    @Override
    public int getClassId() {
        return -350980120;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "webPageEmpty#eb1477e8";
    }
}

