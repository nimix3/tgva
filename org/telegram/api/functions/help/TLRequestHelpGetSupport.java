
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.help.TLSupport;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetSupport
extends TLMethod<TLSupport> {
    public static final int CLASS_ID = -1663104819;

    @Override
    public int getClassId() {
        return -1663104819;
    }

    @Override
    public TLSupport deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLSupport) {
            return (TLSupport)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.help.TLSupport, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "help.getSupport#9cdf08cd";
    }
}

