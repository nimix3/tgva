
package org.telegram.api.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class GzipRequest<T extends TLObject>
extends TLMethod<T> {
    private static final int CLASS_ID = 812830625;
    private TLMethod<T> method;

    public GzipRequest(TLMethod<T> method) {
        this.method = method;
    }

    @Override
    public T deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return this.method.deserializeResponse(stream, context);
    }

    @Override
    public int getClassId() {
        return 812830625;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        ByteArrayOutputStream resOutput = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(resOutput);
        this.method.serialize((OutputStream)gzipOutputStream);
        gzipOutputStream.flush();
        gzipOutputStream.close();
        byte[] body = resOutput.toByteArray();
        StreamingUtils.writeTLBytes(body, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        throw new IOException("Unsupported operation");
    }

    @Override
    public String toString() {
        return "gzip<" + this.method + ">";
    }
}

