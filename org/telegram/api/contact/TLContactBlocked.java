
package org.telegram.api.contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLContactBlocked
extends TLObject {
    public static final int CLASS_ID = 1444661369;
    private int userId;
    private int date;

    @Override
    public int getClassId() {
        return 1444661369;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "contactBlocked#561bc879";
    }
}

