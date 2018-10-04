
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.TLConfig;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetConfig
extends TLMethod<TLConfig> {
    public static final int CLASS_ID = -990308245;

    @Override
    public int getClassId() {
        return -990308245;
    }

    @Override
    public TLConfig deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLConfig) {
            return (TLConfig)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.TLConfig, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public String toString() {
        return "help.getConfig#c4f9186b";
    }
}

