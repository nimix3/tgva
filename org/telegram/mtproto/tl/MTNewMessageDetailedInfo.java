
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTNewMessageDetailedInfo
extends TLObject {
    public static final int CLASS_ID = -2137147681;
    private long answerMsgId;
    private int bytes;
    private int status;

    public MTNewMessageDetailedInfo(long answerMsgId, int bytes, int status) {
        this.answerMsgId = answerMsgId;
        this.bytes = bytes;
        this.status = status;
    }

    public MTNewMessageDetailedInfo() {
    }

    public long getAnswerMsgId() {
        return this.answerMsgId;
    }

    public int getBytes() {
        return this.bytes;
    }

    public int getStatus() {
        return this.status;
    }

    @Override
    public int getClassId() {
        return -2137147681;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.answerMsgId, stream);
        StreamingUtils.writeInt(this.bytes, stream);
        StreamingUtils.writeInt(this.status, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.answerMsgId = StreamingUtils.readLong(stream);
        this.bytes = StreamingUtils.readInt(stream);
        this.status = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "msg_new_detailed_info#809db6df";
    }
}

