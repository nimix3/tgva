
package org.telegram.api.messages.dialogs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dialogs.TLAbsDialogs;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDialogsSlice
extends TLAbsDialogs {
    public static final int CLASS_ID = 1910543603;
    private int count;

    @Override
    public int getClassId() {
        return 1910543603;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int value) {
        this.count = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.dialogs, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.count = StreamingUtils.readInt(stream);
        this.dialogs = StreamingUtils.readTLVector(stream, context);
        this.messages = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.dialogsSlice#71e094f3";
    }
}

