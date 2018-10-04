
package org.telegram.api.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatForbidden
extends TLAbsChat {
    public static final int CLASS_ID = 120753115;
    private String title;

    @Override
    public int getClassId() {
        return 120753115;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.title, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "chatForbidden#7328bdb";
    }
}

