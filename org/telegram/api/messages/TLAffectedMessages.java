
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAffectedMessages
extends TLObject {
    public static final int CLASS_ID = -2066640507;
    private int pts;
    private int ptsCount;

    @Override
    public int getClassId() {
        return -2066640507;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.affectedmessages#84d19185";
    }
}

