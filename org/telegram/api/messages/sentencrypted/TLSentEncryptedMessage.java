
package org.telegram.api.messages.sentencrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.sentencrypted.TLAbsSentEncryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSentEncryptedMessage
extends TLAbsSentEncryptedMessage {
    public static final int CLASS_ID = 1443858741;

    @Override
    public int getClassId() {
        return 1443858741;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.sentEncryptedMessage#560f8935";
    }
}

