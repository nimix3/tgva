
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountResetNotifySettings
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -612493497;

    @Override
    public int getClassId() {
        return -612493497;
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

    @Override
    public String toString() {
        return "account.resetNotifySettings#db7e1747";
    }
}

