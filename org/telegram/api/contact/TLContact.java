
package org.telegram.api.contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLContact
extends TLObject {
    public static final int CLASS_ID = -116274796;
    private int userId;
    private boolean mutual;

    @Override
    public int getClassId() {
        return -116274796;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
    }

    public boolean getMutual() {
        return this.mutual;
    }

    public void setMutual(boolean value) {
        this.mutual = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLBool(this.mutual, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.mutual = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "contact#f911c994";
    }
}

