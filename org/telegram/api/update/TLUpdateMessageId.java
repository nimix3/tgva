
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateMessageId
extends TLAbsUpdate {
    public static final int CLASS_ID = 1318109142;
    private int id;
    private long randomId;

    @Override
    public int getClassId() {
        return 1318109142;
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long randomId) {
        this.randomId = randomId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeLong(this.randomId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.randomId = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "updateMessageID#4e90bfd6";
    }
}

