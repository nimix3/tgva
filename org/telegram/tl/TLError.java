
package org.telegram.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLError
extends TLObject {
    public static final int CLASS_ID = -994444869;
    public String text;
    protected int code;

    @Override
    public int getClassId() {
        return -994444869;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.code, stream);
        StreamingUtils.writeTLString(this.text, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.code = StreamingUtils.readInt(stream);
        this.text = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "tlError#c4b9f9bb";
    }
}

