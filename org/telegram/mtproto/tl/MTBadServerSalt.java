
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.MTBadMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class MTBadServerSalt
extends MTBadMessage {
    public static final int CLASS_ID = -307542917;

    public MTBadServerSalt(long messageId, int seqNo, int errorNo, long newSalt) {
        this.badMsgId = messageId;
        this.badMsqSeqno = seqNo;
        this.errorCode = errorNo;
        this.newServerSalt = newSalt;
    }

    public MTBadServerSalt() {
        this.badMsgId = 0L;
        this.badMsqSeqno = 0;
        this.errorCode = 0;
        this.newServerSalt = 0L;
    }

    @Override
    public long getNewServerSalt() {
        return this.newServerSalt;
    }

    @Override
    public int getClassId() {
        return -307542917;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.badMsgId, stream);
        StreamingUtils.writeInt(this.badMsqSeqno, stream);
        StreamingUtils.writeInt(this.errorCode, stream);
        StreamingUtils.writeLong(this.newServerSalt, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.badMsgId = StreamingUtils.readLong(stream);
        this.badMsqSeqno = StreamingUtils.readInt(stream);
        this.errorCode = StreamingUtils.readInt(stream);
        this.newServerSalt = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "bad_server_salt#edab447b";
    }
}

