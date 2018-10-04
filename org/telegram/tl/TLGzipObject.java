
package org.telegram.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLGzipObject
extends TLObject {
    public static final int CLASS_ID = 812830625;
    private byte[] packedData;

    public TLGzipObject(byte[] packedData) {
        this.packedData = packedData;
    }

    public TLGzipObject() {
    }

    @Override
    public int getClassId() {
        return 812830625;
    }

    public byte[] getPackedData() {
        return this.packedData;
    }

    public void setPackedData(byte[] packedData) {
        this.packedData = packedData;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.packedData, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.packedData = StreamingUtils.readTLBytes(stream);
    }

    @Override
    public String toString() {
        return "gzip_packed#3072cfa1";
    }
}

