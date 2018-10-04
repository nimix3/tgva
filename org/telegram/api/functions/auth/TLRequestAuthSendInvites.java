
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLStringVector;

public class TLRequestAuthSendInvites
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 1998331287;
    private TLStringVector phoneNumbers;
    private String message;

    @Override
    public int getClassId() {
        return 1998331287;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    public TLStringVector getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(TLStringVector value) {
        this.phoneNumbers = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.phoneNumbers, stream);
        StreamingUtils.writeTLString(this.message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumbers = StreamingUtils.readTLStringVector(stream, context);
        this.message = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.sendInvites#771c1d97";
    }
}

