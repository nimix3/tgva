
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.account.TLAccountAuthorizations;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetAuthorizations
extends TLMethod<TLAccountAuthorizations> {
    public static final int CLASS_ID = -484392616;

    @Override
    public int getClassId() {
        return -484392616;
    }

    @Override
    public TLAccountAuthorizations deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAccountAuthorizations) {
            return (TLAccountAuthorizations)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.account.TLAuthorizations, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "account.getAuthorizations#e320c158";
    }
}

