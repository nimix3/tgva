
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.api.sendmessage.action.TLAbsSendMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionTyping
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = 206520510;
    private TLAbsSendMessageAction action;

    public TLDecryptedMessageActionTyping() {
    }

    public TLDecryptedMessageActionTyping(TLAbsSendMessageAction action) {
        this.action = action;
    }

    @Override
    public int getClassId() {
        return 206520510;
    }

    public TLAbsSendMessageAction getAction() {
        return this.action;
    }

    public void setAction(TLAbsSendMessageAction action) {
        this.action = action;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.action = (TLAbsSendMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionSetMessageTTL#c4f40be";
    }
}

