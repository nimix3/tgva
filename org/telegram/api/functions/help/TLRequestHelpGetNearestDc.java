
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.TLNearestDc;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetNearestDc
extends TLMethod<TLNearestDc> {
    public static final int CLASS_ID = 531836966;

    @Override
    public int getClassId() {
        return 531836966;
    }

    @Override
    public TLNearestDc deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLNearestDc) {
            return (TLNearestDc)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.TLNearestDc, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "help.getNearestDc#1fb33026";
    }
}

