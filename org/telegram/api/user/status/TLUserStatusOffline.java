
package org.telegram.api.user.status;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.status.TLAbsUserStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUserStatusOffline
extends TLAbsUserStatus {
    public static final int CLASS_ID = 9203775;
    private int wasOnline;

    @Override
    public int getClassId() {
        return 9203775;
    }

    public int getWasOnline() {
        return this.wasOnline;
    }

    public void setWasOnline(int wasOnline) {
        this.wasOnline = wasOnline;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.wasOnline, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.wasOnline = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "userStatusOffline#8c703f";
    }
}

