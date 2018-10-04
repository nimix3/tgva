
package org.telegram.api.messages.dialogs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dialogs.TLAbsDialogs;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDialogs
extends TLAbsDialogs {
    public static final int CLASS_ID = 364538944;

    @Override
    public int getClassId() {
        return 364538944;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.dialogs, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.dialogs = StreamingUtils.readTLVector(stream, context);
        this.messages = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.dialogs#15ba6c40";
    }
}

