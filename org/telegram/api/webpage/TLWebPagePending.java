
package org.telegram.api.webpage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.webpage.TLAbsWebPage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLWebPagePending
extends TLAbsWebPage {
    public static final int CLASS_ID = -981018084;
    private long id;
    private int date;

    @Override
    public int getClassId() {
        return -981018084;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "webPagePending#c586da1c";
    }
}

