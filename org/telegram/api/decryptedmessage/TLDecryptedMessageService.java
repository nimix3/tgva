
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessage;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageService
extends TLAbsDecryptedMessage {
    public static final int CLASS_ID = 1930838368;
    private TLDecryptedMessageAction action;

    @Override
    public int getClassId() {
        return 1930838368;
    }

    public TLDecryptedMessageAction getAction() {
        return this.action;
    }

    public void setAction(TLDecryptedMessageAction action) {
        this.action = action;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomId = StreamingUtils.readLong(stream);
        this.action = (TLDecryptedMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageService#73164160";
    }
}

