
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLSentCode;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountSendChangePhoneCode
extends TLMethod<TLSentCode> {
    public static final int CLASS_ID = 149257707;
    private static final int FLAG_ALLOW_FLASHCALL = 1;
    private int flags;
    private String phoneNumber;
    private boolean currentNumber = false;

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

    @Override
    public int getClassId() {
        return 149257707;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLBool(this.currentNumber, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.phoneNumber = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.currentNumber = StreamingUtils.readTLBool(stream);
        }
    }

    @Override
    public String toString() {
        return "account.sendChangePhoneCode#8e57deb";
    }
}

