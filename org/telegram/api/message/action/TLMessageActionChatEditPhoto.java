
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageActionChatEditPhoto
extends TLAbsMessageAction {
    public static final int CLASS_ID = 2144015272;
    private TLAbsPhoto photo;

    @Override
    public int getClassId() {
        return 2144015272;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsPhoto photo) {
        this.photo = photo;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.photo, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messageActionChatEditPhoto#7fcb13a8";
    }
}

