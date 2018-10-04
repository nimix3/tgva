
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.sendmessage.action.TLAbsSendMessageAction;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateUserTyping
extends TLAbsUpdate {
    public static final int CLASS_ID = 1548249383;
    private int userId;
    private TLAbsSendMessageAction action;

    @Override
    public int getClassId() {
        return 1548249383;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TLAbsSendMessageAction getAction() {
        return this.action;
    }

    public void setAction(TLAbsSendMessageAction action) {
        this.action = action;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.action = (TLAbsSendMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateUserTyping#5c486927";
    }
}

