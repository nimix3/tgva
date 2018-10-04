
package org.telegram.api.message.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageActionChatEditTitle
extends TLAbsMessageAction {
    public static final int CLASS_ID = -1247687078;
    private String title;

    @Override
    public int getClassId() {
        return -1247687078;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.title, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.title = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messageActionChatEditTitle#b5a1ce5a";
    }
}

