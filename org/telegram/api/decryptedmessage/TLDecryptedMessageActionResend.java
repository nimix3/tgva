
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionResend
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = 1360072880;
    private int startSeqNo;
    private int endSeqNo;

    public TLDecryptedMessageActionResend() {
    }

    public TLDecryptedMessageActionResend(int startSeqNo, int endSeqNo) {
        this.startSeqNo = startSeqNo;
        this.endSeqNo = endSeqNo;
    }

    @Override
    public int getClassId() {
        return 1360072880;
    }

    public int getStartSeqNo() {
        return this.startSeqNo;
    }

    public void setStartSeqNo(int startSeqNo) {
        this.startSeqNo = startSeqNo;
    }

    public int getEndSeqNo() {
        return this.endSeqNo;
    }

    public void setEndSeqNo(int endSeqNo) {
        this.endSeqNo = endSeqNo;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.startSeqNo, stream);
        StreamingUtils.writeInt(this.endSeqNo, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.startSeqNo = StreamingUtils.readInt(stream);
        this.endSeqNo = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionResend#511110b0";
    }
}

