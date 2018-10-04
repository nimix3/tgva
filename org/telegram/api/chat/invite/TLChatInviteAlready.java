
package org.telegram.api.chat.invite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatInviteAlready
extends TLAbsChatInvite {
    public static final int CLASS_ID = 1516793212;
    private TLAbsChat chat;

    @Override
    public int getClassId() {
        return 1516793212;
    }

    public TLAbsChat getChat() {
        return this.chat;
    }

    public void setChat(TLAbsChat chat) {
        this.chat = chat;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeTLObject(this.chat, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.chat = (TLAbsChat)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "chat.chatInviteAlready#5a686d7c";
    }
}

