
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLExportedAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthExportAuthorization
extends TLMethod<TLExportedAuthorization> {
    public static final int CLASS_ID = -440401971;
    private int dcId;

    @Override
    public int getClassId() {
        return -440401971;
    }

    @Override
    public TLExportedAuthorization deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLExportedAuthorization) {
            return (TLExportedAuthorization)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.auth.TLExportedAuthorization, got: " + res.getClass().getCanonicalName());
    }

    public int getDcId() {
        return this.dcId;
    }

    public void setDcId(int value) {
        this.dcId = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.dcId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.dcId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "auth.exportAuthorization#e5bfffcd";
    }
}

