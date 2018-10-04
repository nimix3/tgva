
package org.telegram.api.peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLPeerSettings
extends TLObject {
    public static final int CLASS_ID = -2122045747;
    private static final int FLAG_REPORT_SPAM = 1;
    private int flags;

    @Override
    public int getClassId() {
        return -2122045747;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "peerSettings#818426cd";
    }
}

