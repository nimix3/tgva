
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.util.BytesCache;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTRpcResult
extends TLObject {
    public static final int CLASS_ID = -212046591;
    private long messageId;
    private byte[] content;
    private int contentLen;

    public MTRpcResult(long messageId, byte[] content, int contentLen) {
        this.messageId = messageId;
        this.content = content;
        this.contentLen = contentLen;
    }

    public MTRpcResult() {
    }

    public long getMessageId() {
        return this.messageId;
    }

    public byte[] getContent() {
        return this.content;
    }

    public int getContentLen() {
        return this.contentLen;
    }

    @Override
    public int getClassId() {
        return -212046591;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.messageId, stream);
        StreamingUtils.writeByteArray(this.content, 0, this.contentLen, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.messageId = StreamingUtils.readLong(stream);
        int contentSize = stream.available();
        this.content = BytesCache.getInstance().allocate(contentSize);
        StreamingUtils.readBytes(this.content, 0, contentSize, stream);
    }

    @Override
    public String toString() {
        return "rpc_result#f35c6d01";
    }
}

