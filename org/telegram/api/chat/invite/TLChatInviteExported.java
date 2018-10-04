
package org.telegram.api.chat.invite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatInviteExported
extends TLAbsChatInvite {
    public static final int CLASS_ID = -64092740;
    private String link;

    @Override
    public int getClassId() {
        return -64092740;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeTLString(this.link, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.link = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "chat.chatInviteExported#fc2e05bc";
    }
}

