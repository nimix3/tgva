
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageMediaContact
extends TLAbsDecryptedMessageMedia {
    public static final int CLASS_ID = 1485441687;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private int userId;

    @Override
    public int getClassId() {
        return 1485441687;
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

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.firstName, stream);
        StreamingUtils.writeTLString(this.lastName, stream);
        StreamingUtils.writeInt(this.userId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.firstName = StreamingUtils.readTLString(stream);
        this.lastName = StreamingUtils.readTLString(stream);
        this.userId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageMediaContact#588a0a97";
    }
}

