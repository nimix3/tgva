
package org.telegram.api.input.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputUser
extends TLAbsInputUser {
    public static final int CLASS_ID = -668391402;
    private int userId;
    private long accessHash;

    @Override
    public int getClassId() {
        return -668391402;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "inputUser#d8292816";
    }
}

