
package org.telegram.api.functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestInvokeAfterMsg
extends TLMethod<TLObject> {
    public static final int CLASS_ID = -878758099;
    private long msgId;
    private TLMethod query;

    @Override
    public int getClassId() {
        return -878758099;
    }

    @Override
    public TLObject deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return this.query.deserializeResponse(stream, context);
    }

    public long getMsgId() {
        return this.msgId;
    }

    public void setMsgId(long value) {
        this.msgId = value;
    }

    public TLMethod getQuery() {
        return this.query;
    }

    public void setQuery(TLMethod value) {
        this.query = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.msgId, stream);
        StreamingUtils.writeTLMethod(this.query, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.msgId = StreamingUtils.readLong(stream);
        this.query = StreamingUtils.readTLMethod(stream, context);
    }

    @Override
    public String toString() {
        return "invokeAfterMsg#cb9f372d";
    }
}

