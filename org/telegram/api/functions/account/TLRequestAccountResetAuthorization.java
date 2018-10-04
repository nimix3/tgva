
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountPrivacyRules;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountResetAuthorization
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -545786948;
    private long hash;

    @Override
    public int getClassId() {
        return -545786948;
    }

    public long getHash() {
        return this.hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAccountPrivacyRules) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.privacy.TLBool, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "account.resetAuthorization#df77f3bc";
    }
}

