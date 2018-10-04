
package org.telegram.api.functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestInvokeAfterMsgs
extends TLMethod<TLObject> {
    public static final int CLASS_ID = 1036301552;
    private TLLongVector msgIds;
    private TLMethod query;

    @Override
    public int getClassId() {
        return 1036301552;
    }

    @Override
    public TLObject deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return this.query.deserializeResponse(stream, context);
    }

    public TLLongVector getMsgIds() {
        return this.msgIds;
    }

    public void setMsgIds(TLLongVector value) {
        this.msgIds = value;
    }

    public TLMethod getQuery() {
        return this.query;
    }

    public void setQuery(TLMethod value) {
        this.query = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.msgIds, stream);
        StreamingUtils.writeTLMethod(this.query, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.msgIds = StreamingUtils.readTLLongVector(stream, context);
        this.query = StreamingUtils.readTLMethod(stream, context);
    }

    @Override
    public String toString() {
        return "invokeAfterMsgs#3dc4b4f0";
    }
}

