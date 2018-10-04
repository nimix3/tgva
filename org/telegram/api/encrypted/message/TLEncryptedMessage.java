
package org.telegram.api.encrypted.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.file.TLAbsEncryptedFile;
import org.telegram.api.encrypted.message.TLAbsEncryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLEncryptedMessage
extends TLAbsEncryptedMessage {
    public static final int CLASS_ID = -317144808;

    @Override
    public int getClassId() {
        return -317144808;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLBytes(this.bytes, stream);
        StreamingUtils.writeTLObject(this.file, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomId = StreamingUtils.readLong(stream);
        this.chatId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.bytes = StreamingUtils.readTLBytes(stream, context);
        this.file = (TLAbsEncryptedFile)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "encryptedMessage#ed18c118";
    }
}

