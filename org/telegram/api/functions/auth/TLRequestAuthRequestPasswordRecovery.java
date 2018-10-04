
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.auth.TLPasswordRecovery;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthRequestPasswordRecovery
extends TLMethod<TLPasswordRecovery> {
    public static final int CLASS_ID = -661144474;

    @Override
    public int getClassId() {
        return -661144474;
    }

    @Override
    public TLPasswordRecovery deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLPasswordRecovery) {
            return (TLPasswordRecovery)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.auth.TLPasswordRecovery, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "account.requestPasswordRecovery#d897bc66";
    }
}

