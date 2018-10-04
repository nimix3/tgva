
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLMessageRange
extends TLObject {
    public static final int CLASS_ID = 182649427;
    private int minId;
    private int maxId;

    @Override
    public int getClassId() {
        return 182649427;
    }

    public int getMinId() {
        return this.minId;
    }

    public void setMinId(int minId) {
        this.minId = minId;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.minId, stream);
        StreamingUtils.writeInt(this.maxId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.minId = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "message.TLMessageRange#ae30253";
    }
}

