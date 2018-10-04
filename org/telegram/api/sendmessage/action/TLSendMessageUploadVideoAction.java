
package org.telegram.api.sendmessage.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.sendmessage.action.TLAbsSendMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSendMessageUploadVideoAction
extends TLAbsSendMessageAction {
    public static final int CLASS_ID = -378127636;
    private int progress;

    @Override
    public int getClassId() {
        return -378127636;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeInt(this.progress, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.progress = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "sendMessageUploadVideoAction#e9763aec";
    }
}

