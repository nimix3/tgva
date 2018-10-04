
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTMessageDetailedInfo
extends TLObject {
    public static final int CLASS_ID = 661470918;
    private long msgId;
    private long answerMsgId;
    private int bytes;
    private int state;

    public MTMessageDetailedInfo(long msgId, long answerMsgId, int bytes, int state) {
        this.msgId = msgId;
        this.answerMsgId = answerMsgId;
        this.bytes = bytes;
        this.state = state;
    }

    public MTMessageDetailedInfo() {
    }

    public long getMsgId() {
        return this.msgId;
    }

    public long getAnswerMsgId() {
        return this.answerMsgId;
    }

    public int getBytes() {
        return this.bytes;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public int getClassId() {
        return 661470918;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.msgId, stream);
        StreamingUtils.writeLong(this.answerMsgId, stream);
        StreamingUtils.writeInt(this.bytes, stream);
        StreamingUtils.writeInt(this.state, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.msgId = StreamingUtils.readLong(stream);
        this.answerMsgId = StreamingUtils.readLong(stream);
        this.bytes = StreamingUtils.readInt(stream);
        this.state = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "msg_detailed_info#276d3ec6";
    }
}

