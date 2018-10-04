
package org.telegram.api.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatEmpty
extends TLAbsChat {
    public static final int CLASS_ID = -1683826688;

    @Override
    public int getClassId() {
        return -1683826688;
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
        return "chatEmpty#9ba2d800";
    }
}

