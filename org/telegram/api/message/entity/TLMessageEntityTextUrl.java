
package org.telegram.api.message.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageEntityTextUrl
extends TLAbsMessageEntity {
    public static final int CLASS_ID = 1990644519;
    private int offset;
    private int length;
    private String url;

    @Override
    public int getClassId() {
        return 1990644519;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.length, stream);
        StreamingUtils.writeTLString(this.url, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offset = StreamingUtils.readInt(stream);
        this.length = StreamingUtils.readInt(stream);
        this.url = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "message.entity.MessageEntityTextUrl#76a6d327";
    }
}

