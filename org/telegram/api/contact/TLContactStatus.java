
package org.telegram.api.contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.status.TLAbsUserStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLContactStatus
extends TLObject {
    public static final int CLASS_ID = -748155807;
    private int userId;
    private TLAbsUserStatus status;

    @Override
    public int getClassId() {
        return -748155807;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
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
        return "contactStatus#d3680c61";
    }
}

