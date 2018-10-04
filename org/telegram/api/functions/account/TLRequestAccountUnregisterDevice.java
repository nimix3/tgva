
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountUnregisterDevice
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 1707432768;
    private int tokenType;
    private String token;

    @Override
    public int getClassId() {
        return 1707432768;
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

    public int getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(int value) {
        this.tokenType = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.tokenType, stream);
        StreamingUtils.writeTLString(this.token, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.tokenType = StreamingUtils.readInt(stream);
        this.token = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "account.unregisterDevice#65c55b40";
    }
}

