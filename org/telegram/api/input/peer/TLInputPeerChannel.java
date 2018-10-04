
package org.telegram.api.input.peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputPeerChannel
extends TLAbsInputPeer {
    public static final int CLASS_ID = 548253432;
    private int channelId;
    private long accessHash;

    @Override
    public int getClassId() {
        return 548253432;
    }

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
        return "inputPeerChannel#20adaef8";
    }
}

