
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.TLContactsFound;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsSearch
extends TLMethod<TLContactsFound> {
    public static final int CLASS_ID = 301470424;
    private String q;
    private int limit;

    @Override
    public int getClassId() {
        return 301470424;
    }

    @Override
    public TLContactsFound deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLContactsFound) {
            return (TLContactsFound)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.contacts.TLFound, got: " + res.getClass().getCanonicalName());
    }

    public String getQ() {
        return this.q;
    }

    public void setQ(String value) {
        this.q = value;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.q, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.q = StreamingUtils.readTLString(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "contacts.search#11f812d8";
    }
}

