
package org.telegram.api.message.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageEntityMention
extends TLAbsMessageEntity {
    public static final int CLASS_ID = -100378723;
    private int offset;
    private int length;

    @Override
    public int getClassId() {
        return -100378723;
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

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.length, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offset = StreamingUtils.readInt(stream);
        this.length = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "message.entity.MessageEntityMention#fa04579d";
    }
}

