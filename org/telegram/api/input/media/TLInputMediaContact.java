
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaContact
extends TLAbsInputMedia {
    public static final int CLASS_ID = -1494984313;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    @Override
    public int getClassId() {
        return -1494984313;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.firstName, stream);
        StreamingUtils.writeTLString(this.lastName, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.firstName = StreamingUtils.readTLString(stream);
        this.lastName = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaContact#a6e45987";
    }
}

