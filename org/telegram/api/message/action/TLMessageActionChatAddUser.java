
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;

public class TLMessageActionChatAddUser
extends TLAbsMessageAction {
    public static final int CLASS_ID = 1217033015;
    private TLIntVector users;

    @Override
    public int getClassId() {
        return 1217033015;
    }

    public TLIntVector getUsers() {
        return this.users;
    }

    public void setUsers(TLIntVector users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.users = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "messageActionChatAddUser#488a7337";
    }
}

