
package org.telegram.api.message.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageEntityPre
extends TLAbsMessageEntity {
    public static final int CLASS_ID = 1938967520;
    private int offset;
    private int length;
    private String language;

    @Override
    public int getClassId() {
        return 1938967520;
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

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.length, stream);
        StreamingUtils.writeTLString(this.language, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offset = StreamingUtils.readInt(stream);
        this.length = StreamingUtils.readInt(stream);
        this.language = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "message.entity.MessageEntityPre#73924be0";
    }
}

