
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.util.BytesCache;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTMessage
extends TLObject {
    private long messageId;
    private int seqNo;
    private byte[] content;
    private int contentLen;

    public MTMessage(long messageId, int seqNo, byte[] content) {
        this(messageId, seqNo, content, content.length);
    }

    public MTMessage(long messageId, int seqNo, byte[] content, int contentLen) {
        this.messageId = messageId;
        this.seqNo = seqNo;
        this.content = content;
        this.contentLen = contentLen;
    }

    public MTMessage() {
    }

    @Override
    public int getClassId() {
        return 0;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getContentLen() {
        return this.contentLen;
    }

    public void setContentLen(int contentLen) {
        this.contentLen = contentLen;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.messageId, stream);
        StreamingUtils.writeInt(this.seqNo, stream);
        StreamingUtils.writeInt(this.content.length, stream);
        StreamingUtils.writeByteArray(this.content, 0, this.contentLen, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.messageId = StreamingUtils.readLong(stream);
        this.seqNo = StreamingUtils.readInt(stream);
        int size = StreamingUtils.readInt(stream);
        this.content = BytesCache.getInstance().allocate(size);
        StreamingUtils.readBytes(this.content, 0, size, stream);
    }

    @Override
    public String toString() {
        return "message";
    }
}

