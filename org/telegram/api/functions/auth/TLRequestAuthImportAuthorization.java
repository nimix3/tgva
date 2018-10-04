
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthImportAuthorization
extends TLMethod<TLAuthorization> {
    public static final int CLASS_ID = -470837741;
    private int id;
    private TLBytes bytes;

    @Override
    public int getClassId() {
        return -470837741;
    }

    @Override
    public TLAuthorization deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAuthorization) {
            return (TLAuthorization)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.auth.TLAuthorization, got: " + res.getClass().getCanonicalName());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public TLBytes getBytes() {
        return this.bytes;
    }

    public void setBytes(TLBytes value) {
        this.bytes = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLBytes(this.bytes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.bytes = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "auth.importAuthorization#e3ef9613";
    }
}

