
package org.telegram.api.contacts.blocked;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.blocked.TLAbsBlocked;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLBlockedSlice
extends TLAbsBlocked {
    public static final int CLASS_ID = -1878523231;
    private int count;

    @Override
    public int getClassId() {
        return -1878523231;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int value) {
        this.count = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.blocked, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.count = StreamingUtils.readInt(stream);
        this.blocked = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.blockedSlice#900802a1";
    }
}

