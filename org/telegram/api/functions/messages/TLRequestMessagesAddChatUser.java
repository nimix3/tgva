
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesAddChatUser
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -106911223;
    private int chatId;
    private TLAbsInputUser userId;
    private int fwdLimit;

    @Override
    public int getClassId() {
        return -106911223;
    }

    @Override
    public TLAbsUpdates deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdates) {
            return (TLAbsUpdates)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public TLAbsInputUser getUserId() {
        return this.userId;
    }

    public void setUserId(TLAbsInputUser value) {
        this.userId = value;
    }

    public int getFwdLimit() {
        return this.fwdLimit;
    }

    public void setFwdLimit(int value) {
        this.fwdLimit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeInt(this.fwdLimit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.fwdLimit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.addChatUser#f9a0aa09";
    }
}

