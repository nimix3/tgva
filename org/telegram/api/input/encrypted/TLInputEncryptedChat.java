
package org.telegram.api.input.encrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputEncryptedChat
extends TLObject {
    public static final int CLASS_ID = -247351839;
    private int chatId;
    private long accessHash;

    @Override
    public int getClassId() {
        return -247351839;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int value) {
        this.chatId = value;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long value) {
        this.accessHash = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "inputEncryptedChat#f141b5e1";
    }
}

