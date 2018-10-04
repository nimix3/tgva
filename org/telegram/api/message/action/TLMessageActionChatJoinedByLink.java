
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageActionChatJoinedByLink
extends TLAbsMessageAction {
    public static final int CLASS_ID = -123931160;
    private int inviterId;

    @Override
    public int getClassId() {
        return -123931160;
    }

    public int getInviterId() {
        return this.inviterId;
    }

    public void setInviterId(int inviterId) {
        this.inviterId = inviterId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.inviterId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.inviterId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messageaction.messageActionChatJoinedByLink#f89cf5e8";
    }
}

