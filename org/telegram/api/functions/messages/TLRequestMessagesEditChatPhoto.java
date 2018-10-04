
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.photo.TLAbsInputChatPhoto;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesEditChatPhoto
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -900957736;
    private int chatId;
    private TLAbsInputChatPhoto photo;

    @Override
    public int getClassId() {
        return -900957736;
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

    public TLAbsInputChatPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsInputChatPhoto value) {
        this.photo = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.photo = (TLAbsInputChatPhoto)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.editChatPhoto#ca4c79d8";
    }
}

