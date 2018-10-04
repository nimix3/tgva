
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountDaysTTL;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountSetAccountTTL
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 608323678;
    private TLAccountDaysTTL ttl;

    @Override
    public int getClassId() {
        return 608323678;
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

    public TLAccountDaysTTL getTtl() {
        return this.ttl;
    }

    public void setTtl(TLAccountDaysTTL ttl) {
        this.ttl = ttl;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.ttl, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.ttl = (TLAccountDaysTTL)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.setAccountTTL#2442485e";
    }
}

