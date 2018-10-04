
package org.telegram.api.auth.sentcodetype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.sentcodetype.TLAbsSentCodeType;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSentCodeTypeFlashCall
extends TLAbsSentCodeType {
    public static final int CLASS_ID = -1425815847;
    protected int pattern;

    public int getPattern() {
        return this.pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    @Override
    public int getClassId() {
        return -1425815847;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.pattern, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pattern = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "auth.sentCodeTypeFlashCall#ab03c6d9";
    }
}

