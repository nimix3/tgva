
package org.telegram.api.encrypted.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLEncryptedChatDiscarded
extends TLAbsEncryptedChat {
    public static final int CLASS_ID = 332848423;

    @Override
    public int getClassId() {
        return 332848423;
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
        return "encryptedChatDiscarded#13d6dd27";
    }
}

