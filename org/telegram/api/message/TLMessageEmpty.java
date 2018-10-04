
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageEmpty
extends TLAbsMessage {
    public static final int CLASS_ID = -2082087340;
    private int id;

    @Override
    public int getClassId() {
        return -2082087340;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getChatId() {
        return 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messageEmpty#83e5de54";
    }
}

