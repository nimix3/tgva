
package org.telegram.api.encrypted.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.message.TLAbsEncryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLEncryptedMessageService
extends TLAbsEncryptedMessage {
    public static final int CLASS_ID = 594758406;

    @Override
    public int getClassId() {
        return 594758406;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLBytes(this.bytes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomId = StreamingUtils.readLong(stream);
        this.chatId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.bytes = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "encryptedMessageService#23734b06";
    }
}

