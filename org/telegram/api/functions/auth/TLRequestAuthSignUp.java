
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthSignUp
extends TLMethod<TLAuthorization> {
    public static final int CLASS_ID = 453408308;
    private String phoneNumber;
    private String phoneCodeHash;
    private String phoneCode;
    private String firstName;
    private String lastName;

    @Override
    public int getClassId() {
        return 453408308;
    }

    @Override
    public TLAuthorization deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAuthorization) {
            return (TLAuthorization)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.auth.TLAuthorization, got: " + res.getClass().getCanonicalName());
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    public String getPhoneCodeHash() {
        return this.phoneCodeHash;
    }

    public void setPhoneCodeHash(String value) {
        this.phoneCodeHash = value;
    }

    public String getPhoneCode() {
        return this.phoneCode;
    }

    public void setPhoneCode(String value) {
        this.phoneCode = value;
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
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.phoneCodeHash, stream);
        StreamingUtils.writeTLString(this.phoneCode, stream);
        StreamingUtils.writeTLString(this.firstName, stream);
        StreamingUtils.writeTLString(this.lastName, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.phoneCodeHash = StreamingUtils.readTLString(stream);
        this.phoneCode = StreamingUtils.readTLString(stream);
        this.firstName = StreamingUtils.readTLString(stream);
        this.lastName = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.signUp#1b067634";
    }
}

