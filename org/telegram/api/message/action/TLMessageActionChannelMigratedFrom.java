
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageActionChannelMigratedFrom
extends TLAbsMessageAction {
    public static final int CLASS_ID = -1336546578;
    private String title;
    private int chatId;

    @Override
    public int getClassId() {
        return -1336546578;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeInt(this.chatId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.title = StreamingUtils.readTLString(stream);
        this.chatId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messageActionChannelMigratedFrom#b055eaee";
    }
}

