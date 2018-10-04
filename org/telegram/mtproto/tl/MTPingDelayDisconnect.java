
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTPingDelayDisconnect
extends TLObject {
    public static final int CLASS_ID = -213746804;
    private long pingId;
    private int disconnectDelay;

    public MTPingDelayDisconnect(long pingId, int disconnectDelay) {
        this.pingId = pingId;
        this.disconnectDelay = disconnectDelay;
    }

    public MTPingDelayDisconnect() {
    }

    public long getPingId() {
        return this.pingId;
    }

    public int getDisconnectDelay() {
        return this.disconnectDelay;
    }

    @Override
    public int getClassId() {
        return -213746804;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.pingId, stream);
        StreamingUtils.writeInt(this.disconnectDelay, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pingId = StreamingUtils.readLong(stream);
        this.disconnectDelay = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "ping_delay_disconnect#f3427b8c";
    }
}

