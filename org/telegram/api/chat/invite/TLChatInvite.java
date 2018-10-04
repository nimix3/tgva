
package org.telegram.api.chat.invite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatInvite
extends TLAbsChatInvite {
    public static final int CLASS_ID = -1813406880;
    public static final int FLAG_CHANNEL = 1;
    public static final int FLAG_BROADCAST = 2;
    public static final int FLAG_PUBLIC = 4;
    public static final int FLAG_MEGAGROUP = 8;
    private int flags;
    private String title;

    @Override
    public int getClassId() {
        return -1813406880;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.title, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.flags = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "chat.chatInvite#93e99b60";
    }
}

