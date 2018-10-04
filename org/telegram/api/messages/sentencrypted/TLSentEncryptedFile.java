
package org.telegram.api.messages.sentencrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.file.TLAbsEncryptedFile;
import org.telegram.api.messages.sentencrypted.TLAbsSentEncryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSentEncryptedFile
extends TLAbsSentEncryptedMessage {
    public static final int CLASS_ID = -1802240206;

    @Override
    public int getClassId() {
        return -1802240206;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLObject(this.file, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.date = StreamingUtils.readInt(stream);
        this.file = (TLAbsEncryptedFile)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.sentEncryptedFile#9493ff32";
    }
}

