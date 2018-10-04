
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTDestroySessionNone
extends TLObject {
    public static final int CLASS_ID = 1658015945;
    private Long session_id;

    public MTDestroySessionNone() {
    }

    public MTDestroySessionNone(Long session_id) {
        this.session_id = session_id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.session_id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.session_id = StreamingUtils.readLong(stream);
    }

    public Long getSession_id() {
        return this.session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    @Override
    public int getClassId() {
        return 1658015945;
    }

    @Override
    public String toString() {
        return "MTDestroySessionNone#62d350c9";
    }
}

