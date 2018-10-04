
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.account.TLAccountDaysTTL;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetAccountTTL
extends TLMethod<TLAccountDaysTTL> {
    public static final int CLASS_ID = 150761757;

    @Override
    public int getClassId() {
        return 150761757;
    }

    @Override
    public TLAccountDaysTTL deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAccountDaysTTL) {
            return (TLAccountDaysTTL)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "account.getAccountTTL#8fc711d";
    }
}

