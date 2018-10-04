
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.MTBadMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class MTBadMessageNotification
extends MTBadMessage {
    public static final int CLASS_ID = -1477445615;

    public MTBadMessageNotification(long badMsgId, int badMsqSeqno, int errorCode) {
        this.badMsgId = badMsgId;
        this.badMsqSeqno = badMsqSeqno;
        this.errorCode = errorCode;
    }

    public MTBadMessageNotification() {
    }

    @Override
    public int getClassId() {
        return -1477445615;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.badMsgId, stream);
        StreamingUtils.writeInt(this.badMsqSeqno, stream);
        StreamingUtils.writeInt(this.errorCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.badMsgId = StreamingUtils.readLong(stream);
        this.badMsqSeqno = StreamingUtils.readInt(stream);
        this.errorCode = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "bad_msg_notification#-1477445615";
    }
}

