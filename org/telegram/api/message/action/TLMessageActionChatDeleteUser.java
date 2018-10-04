
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageActionChatDeleteUser
extends TLAbsMessageAction {
    public static final int CLASS_ID = -1297179892;
    private int userId;

    @Override
    public int getClassId() {
        return -1297179892;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messageActionChatDeleteUser#b2ae9b0c";
    }
}

