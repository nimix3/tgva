
package org.telegram.api.contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLImportedContact
extends TLObject {
    public static final int CLASS_ID = -805141448;
    private int userId;
    private long clientId;

    @Override
    public int getClassId() {
        return -805141448;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
    }

    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long value) {
        this.clientId = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeLong(this.clientId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.clientId = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "importedContact#d0028438";
    }
}

