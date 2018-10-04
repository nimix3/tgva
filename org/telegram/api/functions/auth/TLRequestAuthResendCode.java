
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLSentCode;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthResendCode
extends TLMethod<TLSentCode> {
    public static final int CLASS_ID = 1056025023;
    private String phoneNumber;
    private String phoneCodeHash;

    @Override
    public int getClassId() {
        return 1056025023;
    }

    @Override
    public TLSentCode deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLSentCode) {
            return (TLSentCode)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLSentCode.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
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

    public void setPhoneCodeHash(String phoneCodeHash) {
        this.phoneCodeHash = phoneCodeHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.phoneCodeHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.phoneCodeHash = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.resendCode#3ef1a9bf";
    }
}

