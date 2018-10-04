
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesReorderStickerSets
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -1613775824;
    private TLLongVector order;

    @Override
    public int getClassId() {
        return -1613775824;
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
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLLongVector getOrder() {
        return this.order;
    }

    public void setOrder(TLLongVector order) {
        this.order = order;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.order, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.order = StreamingUtils.readTLLongVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.reorderStickerSets#9fcfbc30";
    }
}

