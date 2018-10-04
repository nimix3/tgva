
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAffectedHistory
extends TLObject {
    public static final int CLASS_ID = -1269012015;
    private int pts;
    private int ptsCount;
    private int offset;

    @Override
    public int getClassId() {
        return -1269012015;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int value) {
        this.pts = value;
    }

    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
        StreamingUtils.writeInt(this.offset, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
        this.offset = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.affectedHistory#b45c69d1";
    }
}

