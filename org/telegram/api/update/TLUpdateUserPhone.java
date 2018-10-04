
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateUserPhone
extends TLAbsUpdate {
    public static final int CLASS_ID = 314130811;
    private int userId;
    private String phone;

    @Override
    public int getClassId() {
        return 314130811;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getPhone() {
        return this.phone;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLString(this.phone, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.phone = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "updateUserPhone#12b9417b";
    }
}

