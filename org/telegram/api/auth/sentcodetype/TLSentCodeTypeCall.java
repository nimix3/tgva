
package org.telegram.api.auth.sentcodetype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.sentcodetype.TLAbsSentCodeType;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSentCodeTypeCall
extends TLAbsSentCodeType {
    public static final int CLASS_ID = 1398007207;
    private int length;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int getClassId() {
        return 1398007207;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.length, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.length = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "auth.sentCodeTypeCall#5353e5a7";
    }
}

