
package org.telegram.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLExportedAuthorization
extends TLObject {
    public static final int CLASS_ID = -543777747;
    private int id;
    private TLBytes bytes;

    @Override
    public int getClassId() {
        return -543777747;
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
        return "auth.exportedAuthorization#df969c2d";
    }
}

