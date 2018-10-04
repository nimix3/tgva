
package org.telegram.api.update.encrypted;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.message.TLAbsEncryptedMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateNewEncryptedMessage
extends TLAbsUpdate {
    public static final int CLASS_ID = 314359194;
    private int qts;
    private TLAbsEncryptedMessage message;

    @Override
    public int getClassId() {
        return 314359194;
    }

    public int getQts() {
        return this.qts;
    }

    public void setQts(int qts) {
        this.qts = qts;
    }

    public TLAbsEncryptedMessage getMessage() {
        return this.message;
    }

    public void setMessage(TLAbsEncryptedMessage message) {
        this.message = message;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.message, stream);
        StreamingUtils.writeInt(this.qts, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.message = (TLAbsEncryptedMessage)StreamingUtils.readTLObject(stream, context);
        this.qts = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateNewEncryptedMessage#12bcbd9a";
    }
}

