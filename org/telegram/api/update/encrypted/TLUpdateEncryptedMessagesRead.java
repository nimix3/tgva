
package org.telegram.api.update.encrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateEncryptedMessagesRead
extends TLAbsUpdate {
    public static final int CLASS_ID = 956179895;
    private int chatId;
    private int maxDate;
    private int date;

    @Override
    public int getClassId() {
        return 956179895;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(int maxDate) {
        this.maxDate = maxDate;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeInt(this.maxDate, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.maxDate = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateEncryptedMessagesRead#38fe25b7";
    }
}

