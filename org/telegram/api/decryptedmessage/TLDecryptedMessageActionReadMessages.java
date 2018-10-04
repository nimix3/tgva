
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;

public class TLDecryptedMessageActionReadMessages
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = 206520510;
    private TLLongVector randomIds = new TLLongVector();

    @Override
    public int getClassId() {
        return 206520510;
    }

    public TLLongVector getRandomIds() {
        return this.randomIds;
    }

    public void setRandomIds(TLLongVector randomIds) {
        this.randomIds = randomIds;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.randomIds, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomIds = StreamingUtils.readTLLongVector(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionSetMessageTTL#c4f40be";
    }
}

