
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.invite.TLChatInviteExported;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesExportChatInvite
extends TLMethod<TLChatInviteExported> {
    public static final int CLASS_ID = -1033305414;
    private int chatId;

    @Override
    public int getClassId() {
        return -1033305414;
    }

    @Override
    public TLChatInviteExported deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLChatInviteExported) {
            return (TLChatInviteExported)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLChatInviteExported.class.getName() + ", got: " + res.getClass().getCanonicalName());
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
        return "request.exportChatInvite#7d885289";
    }
}

