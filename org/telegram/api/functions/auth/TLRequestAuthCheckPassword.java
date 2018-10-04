
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthCheckPassword
extends TLMethod<TLAuthorization> {
    public static final int CLASS_ID = 174260510;
    private TLBytes passwordHash;

    @Override
    public int getClassId() {
        return 174260510;
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

    public TLBytes getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(TLBytes passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.passwordHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.passwordHash = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "auth.checkPassword#a63011e";
    }
}

