
package org.telegram.api.encrypted.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLEncryptedChatEmpty
extends TLAbsEncryptedChat {
    public static final int CLASS_ID = -1417756512;

    @Override
    public int getClassId() {
        return -1417756512;
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
        return "encryptedChatEmpty#ab7ec0a0";
    }
}

