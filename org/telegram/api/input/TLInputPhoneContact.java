
package org.telegram.api.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputPhoneContact
extends TLObject {
    public static final int CLASS_ID = -208488460;
    protected long clientId;
    protected String phone;
    protected String firstName;
    protected String lastName;

    @Override
    public int getClassId() {
        return -208488460;
    }

    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long value) {
        this.clientId = value;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.clientId, stream);
        StreamingUtils.writeTLString(this.phone, stream);
        StreamingUtils.writeTLString(this.firstName, stream);
        StreamingUtils.writeTLString(this.lastName, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.clientId = StreamingUtils.readLong(stream);
        this.phone = StreamingUtils.readTLString(stream);
        this.firstName = StreamingUtils.readTLString(stream);
        this.lastName = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputPhoneContact#f392b7f4";
    }
}

