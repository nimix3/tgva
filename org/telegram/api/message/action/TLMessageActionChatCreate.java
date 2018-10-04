
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;

public class TLMessageActionChatCreate
extends TLAbsMessageAction {
    public static final int CLASS_ID = -1503425638;
    private String title;
    private TLIntVector users;

    @Override
    public int getClassId() {
        return -1503425638;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TLIntVector getUsers() {
        return this.users;
    }

    public void setUsers(TLIntVector users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.title = StreamingUtils.readTLString(stream);
        this.users = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "messageActionChatCreate#a6638b9a";
    }
}

