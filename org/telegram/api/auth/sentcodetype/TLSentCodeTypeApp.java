
package org.telegram.api.auth.sentcodetype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.sentcodetype.TLAbsSentCodeType;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLSentCodeTypeApp
extends TLAbsSentCodeType {
    public static final int CLASS_ID = 1035688326;
    private int length;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int getClassId() {
        return 1035688326;
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
        return "auth.sentCodeTypeApp#3dbb5986";
    }
}

