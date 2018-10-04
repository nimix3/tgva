
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLDecryptedMessageLayer
extends TLObject {
    public static final int CLASS_ID = 467867529;
    public TLBytes randomBytes;
    public int layer;
    public int inSeqNo;
    public int outSeqNo;
    public TLAbsDecryptedMessage message;

    @Override
    public int getClassId() {
        return 467867529;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.randomBytes, stream);
        StreamingUtils.writeInt(this.layer, stream);
        StreamingUtils.writeInt(this.inSeqNo, stream);
        StreamingUtils.writeInt(this.outSeqNo, stream);
        StreamingUtils.writeTLObject(this.message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomBytes = StreamingUtils.readTLBytes(stream, context);
        this.layer = StreamingUtils.readInt(stream);
        this.inSeqNo = StreamingUtils.readInt(stream);
        this.outSeqNo = StreamingUtils.readInt(stream);
        this.message = (TLAbsDecryptedMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageLayer#1be31789";
    }
}

