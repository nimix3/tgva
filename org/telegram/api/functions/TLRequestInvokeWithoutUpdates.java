
package org.telegram.api.functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestInvokeWithoutUpdates
extends TLMethod<TLObject> {
    public static final int CLASS_ID = -1080796745;
    private TLMethod query;

    @Override
    public int getClassId() {
        return -1080796745;
    }

    @Override
    public TLObject deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return this.query.deserializeResponse(stream, context);
    }

    public TLMethod getQuery() {
        return this.query;
    }

    public void setQuery(TLMethod value) {
        this.query = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLMethod(this.query, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.query = StreamingUtils.readTLMethod(stream, context);
    }

    @Override
    public String toString() {
        return "invokeWithoutUpdates#bf9459b7";
    }
}

