
package org.telegram.api.update.encrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateEncryption
extends TLAbsUpdate {
    public static final int CLASS_ID = -1264392051;
    private TLAbsEncryptedChat chat;
    private int date;

    @Override
    public int getClassId() {
        return -1264392051;
    }

    public TLAbsEncryptedChat getChat() {
        return this.chat;
    }

    public void setChat(TLAbsEncryptedChat chat) {
        this.chat = chat;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.chat, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chat = (TLAbsEncryptedChat)StreamingUtils.readTLObject(stream, context);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateEncryption#b4a2e88d";
    }
}

