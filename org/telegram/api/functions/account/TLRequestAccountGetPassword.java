
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.account.TLAbsAccountPassword;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetPassword
extends TLMethod<TLAbsAccountPassword> {
    public static final int CLASS_ID = 1418342645;

    @Override
    public int getClassId() {
        return 1418342645;
    }

    @Override
    public TLAbsAccountPassword deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsAccountPassword) {
            return (TLAbsAccountPassword)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLAbsAccountPassword, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "account.getPassword#548a30f5";
    }
}

