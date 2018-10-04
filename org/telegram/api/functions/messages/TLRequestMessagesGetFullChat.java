
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.TLMessagesChatFull;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetFullChat
extends TLMethod<TLMessagesChatFull> {
    public static final int CLASS_ID = 998448230;
    private int chatId;

    @Override
    public int getClassId() {
        return 998448230;
    }

    @Override
    public TLMessagesChatFull deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLMessagesChatFull) {
            return (TLMessagesChatFull)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLMessagesChatFull.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getFullChat#3b831c66";
    }
}

