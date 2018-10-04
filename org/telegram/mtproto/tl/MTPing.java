
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTPing
extends TLObject {
    public static final int CLASS_ID = 2059302892;
    private long pingId;

    public MTPing(long pingId) {
        this.pingId = pingId;
    }

    public MTPing() {
    }

    @Override
    public int getClassId() {
        return 2059302892;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.pingId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pingId = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "ping#7abe77ec";
    }
}

