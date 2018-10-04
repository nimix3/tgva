
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLCheckedPhone;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthCheckPhone
extends TLMethod<TLCheckedPhone> {
    public static final int CLASS_ID = 1877286395;
    private String phoneNumber;

    @Override
    public int getClassId() {
        return 1877286395;
    }

    @Override
    public TLCheckedPhone deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLCheckedPhone) {
            return (TLCheckedPhone)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.auth.TLCheckedPhone, got: " + res.getClass().getCanonicalName());
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.checkPhone#6fe51dfb";
    }
}

