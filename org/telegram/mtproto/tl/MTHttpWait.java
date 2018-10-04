
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTHttpWait
extends TLObject {
    public static final int CLASS_ID = -1835453025;
    private int max_delay;
    private int wait_after;
    private int max_wait;

    public MTHttpWait(int max_delay, int wait_after, int max_wait) {
        this.max_delay = max_delay;
        this.wait_after = wait_after;
        this.max_wait = max_wait;
    }

    public MTHttpWait() {
    }

    public int getMax_delay() {
        return this.max_delay;
    }

    public void setMax_delay(int max_delay) {
        this.max_delay = max_delay;
    }

    public int getWait_after() {
        return this.wait_after;
    }

    public void setWait_after(int wait_after) {
        this.wait_after = wait_after;
    }

    public int getMax_wait() {
        return this.max_wait;
    }

    public void setMax_wait(int max_wait) {
        this.max_wait = max_wait;
    }

    @Override
    public int getClassId() {
        return -1835453025;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.max_delay, stream);
        StreamingUtils.writeInt(this.wait_after, stream);
        StreamingUtils.writeInt(this.max_wait, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.max_delay = StreamingUtils.readInt(stream);
        this.wait_after = StreamingUtils.readInt(stream);
        this.max_wait = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "MTHttpWait#9299359f";
    }
}

