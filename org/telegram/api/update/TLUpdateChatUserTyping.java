
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.sendmessage.action.TLAbsSendMessageAction;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChatUserTyping
extends TLAbsUpdate {
    public static final int CLASS_ID = -1704596961;
    private int chatId;
    private TLAbsSendMessageAction action;
    private int userId;

    @Override
    public int getClassId() {
        return -1704596961;
    }

    public int getChatId() {
        return this.chatId;
    }

    public TLAbsSendMessageAction getAction() {
        return this.action;
    }

    public int getUserId() {
        return this.userId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.action = (TLAbsSendMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateChatUserTyping#9a65ea1f";
    }
}

