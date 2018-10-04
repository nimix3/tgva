
package org.telegram.api.functions.updates;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.updates.TLUpdatesState;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUpdatesGetState
extends TLMethod<TLUpdatesState> {
    public static final int CLASS_ID = -304838614;

    @Override
    public int getClassId() {
        return -304838614;
    }

    @Override
    public TLUpdatesState deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLUpdatesState) {
            return (TLUpdatesState)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.updates.TLState, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "updates.getState#edd4882a";
    }
}

