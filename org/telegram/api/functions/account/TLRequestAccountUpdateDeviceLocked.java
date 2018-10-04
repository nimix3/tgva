
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountUpdateDeviceLocked
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 954152242;
    private int period;

    @Override
    public int getClassId() {
        return 954152242;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
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
        throw new IOException("Incorrect response type. Expected org.telegram.api.privacy.TLBool, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.period, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.period = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "account.updateDeviceLocked#38df3532";
    }
}

