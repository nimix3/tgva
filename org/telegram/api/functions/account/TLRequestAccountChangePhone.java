
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountChangePhone
extends TLMethod<TLAbsUser> {
    public static final int CLASS_ID = 1891839707;
    private String phoneNumber;
    private String phoneCodeHash;
    private String phoneCode;

    @Override
    public int getClassId() {
        return 1891839707;
    }

    @Override
    public TLAbsUser deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUser) {
            return (TLAbsUser)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLAbsUser, got: " + res.getClass().getCanonicalName());
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneCode() {
        return this.phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneCodeHash() {
        return this.phoneCodeHash;
    }

    public void setPhoneCodeHash(String phoneCodeHash) {
        this.phoneCodeHash = phoneCodeHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.phoneCodeHash, stream);
        StreamingUtils.writeTLString(this.phoneCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.phoneCodeHash = StreamingUtils.readTLString(stream);
        this.phoneCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "account.sendChangePhone#70c32edb";
    }
}

