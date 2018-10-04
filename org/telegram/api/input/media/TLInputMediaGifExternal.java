
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaGifExternal
extends TLAbsInputMedia {
    public static final int CLASS_ID = 1212395773;
    protected String url;
    private String q;

    @Override
    public int getClassId() {
        return 1212395773;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQ() {
        return this.q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.url, stream);
        StreamingUtils.writeTLString(this.q, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.url = StreamingUtils.readTLString(stream);
        this.q = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaGifExternal#4843b0fd";
    }
}

