
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTPong
extends TLObject {
    public static final int CLASS_ID = 880243653;
    private long messageId;
    private long pingId;

    public MTPong(long messageId, long pingId) {
        this.messageId = messageId;
        this.pingId = pingId;
    }

    public MTPong() {
    }

    public long getMessageId() {
        return this.messageId;
    }

    public long getPingId() {
        return this.pingId;
    }

    @Override
    public int getClassId() {
        return 880243653;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.messageId, stream);
        StreamingUtils.writeLong(this.pingId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.messageId = StreamingUtils.readLong(stream);
        this.pingId = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "pong#347773c5";
    }
}

