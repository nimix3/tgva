
package org.telegram.api.input.bot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputBotInlineMessageId
extends TLObject {
    public static final int CLASS_ID = -1995686519;
    private int dcId;
    private long id;
    private long accessHash;

    @Override
    public int getClassId() {
        return -1995686519;
    }

    public int getDcId() {
        return this.dcId;
    }

    public long getId() {
        return this.id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.dcId, stream);
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.dcId = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "inputBotInlineMessageID#890c3d89";
    }
}

