
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;

public class TLUpdateStickerSetsOrder
extends TLAbsUpdate {
    public static final int CLASS_ID = -253774767;
    private TLLongVector order;

    @Override
    public int getClassId() {
        return -253774767;
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
        return "updateStickerSetsOrder#f0dfb451";
    }
}

