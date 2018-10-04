
package org.telegram.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLReceivedNotifyMessage
extends TLObject {
    public static final int CLASS_ID = -1551583367;
    private int id;
    private int flags;

    @Override
    public int getClassId() {
        return -1551583367;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeInt(this.flags, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.id = StreamingUtils.readInt(stream);
        this.flags = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.receivedNotifyMessage#a384b779";
    }
}

