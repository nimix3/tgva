
package org.telegram.api.user.status;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.status.TLAbsUserStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUserStatusOnline
extends TLAbsUserStatus {
    public static final int CLASS_ID = -306628279;
    private int expires;

    @Override
    public int getClassId() {
        return -306628279;
    }

    public int getExpires() {
        return this.expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.expires, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.expires = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "userStatusOnline#edb93949";
    }
}

