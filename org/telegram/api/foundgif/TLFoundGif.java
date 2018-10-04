
package org.telegram.api.foundgif;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.foundgif.TLAbsFoundGif;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLFoundGif
extends TLAbsFoundGif {
    public static final int CLASS_ID = 372165663;
    private String url;
    private String thumbUrl;
    private String contentUrl;
    private String contentType;
    private int w;
    private int h;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getContentUrl() {
        return this.contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public int getClassId() {
        return 372165663;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.url, stream);
        StreamingUtils.writeTLString(this.thumbUrl, stream);
        StreamingUtils.writeTLString(this.contentUrl, stream);
        StreamingUtils.writeTLString(this.contentType, stream);
        StreamingUtils.writeInt(this.w, stream);
        StreamingUtils.writeInt(this.h, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.url = StreamingUtils.readTLString(stream);
        this.thumbUrl = StreamingUtils.readTLString(stream);
        this.contentUrl = StreamingUtils.readTLString(stream);
        this.contentType = StreamingUtils.readTLString(stream);
        this.w = StreamingUtils.readInt(stream);
        this.h = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "foundGif#162ecc1f";
    }
}

