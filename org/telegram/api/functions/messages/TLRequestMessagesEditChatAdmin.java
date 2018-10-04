
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesEditChatAdmin
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -1444503762;
    private int chatId;
    private TLAbsInputPeer userId;
    private boolean isAdmin;

    @Override
    public int getClassId() {
        return -1444503762;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public TLAbsInputPeer getUserId() {
        return this.userId;
    }

    public void setUserId(TLAbsInputPeer userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeTLBool(this.isAdmin, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.userId = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.isAdmin = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "messages.editChatAdmin#a9e69f2e";
    }
}

