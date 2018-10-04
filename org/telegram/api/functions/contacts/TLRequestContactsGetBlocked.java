
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.blocked.TLAbsBlocked;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsGetBlocked
extends TLMethod<TLAbsBlocked> {
    public static final int CLASS_ID = -176409329;
    private int offset;
    private int limit;

    @Override
    public int getClassId() {
        return -176409329;
    }

    @Override
    public TLAbsBlocked deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsBlocked) {
            return (TLAbsBlocked)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.contacts.blocked.TLAbsBlocked, got: " + res.getClass().getCanonicalName());
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.offset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "contacts.getBlocked#f57c350f";
    }
}

