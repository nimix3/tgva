
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthRecoverPassword
extends TLMethod<TLAuthorization> {
    public static final int CLASS_ID = 1319464594;
    private String code;

    @Override
    public int getClassId() {
        return 1319464594;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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
        throw new IOException("Incorrect response type. Expected org.telegram.tl.auth.TLAuthorization, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.code, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.code = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.recoveryPassword#4ea56e92";
    }
}

