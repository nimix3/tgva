
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.user.status.TLAbsUserStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateUserStatus
extends TLAbsUpdate {
    public static final int CLASS_ID = 469489699;
    private int userId;
    private TLAbsUserStatus status;

    @Override
    public int getClassId() {
        return 469489699;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TLAbsUserStatus getStatus() {
        return this.status;
    }

    public void setStatus(TLAbsUserStatus status) {
        this.status = status;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.status, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.status = (TLAbsUserStatus)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateUserStatus#1bfbd823";
    }
}

