
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateNewAuthorization
extends TLAbsUpdate {
    public static final int CLASS_ID = -1895411046;
    private long authKeyId;
    private String location;
    private String device;
    private int date;

    @Override
    public int getClassId() {
        return -1895411046;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getAuthKeyId() {
        return this.authKeyId;
    }

    public void setAuthKeyId(long authKeyId) {
        this.authKeyId = authKeyId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.authKeyId, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLString(this.device, stream);
        StreamingUtils.writeTLString(this.location, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.authKeyId = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
        this.device = StreamingUtils.readTLString(stream);
        this.location = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "updateNewAuthorization#8f06529a";
    }
}

