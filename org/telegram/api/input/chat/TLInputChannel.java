
package org.telegram.api.input.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputChannel
extends TLAbsInputChannel {
    public static final int CLASS_ID = -1343524562;
    private int channelId;
    private long accessHash;

    @Override
    public int getClassId() {
        return -1343524562;
    }

    @Override
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.channelId, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channelId = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "input.chat.TLInputChannel#afeb712e";
    }
}

