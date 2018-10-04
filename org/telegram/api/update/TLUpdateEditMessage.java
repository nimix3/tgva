
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateEditMessage
extends TLAbsUpdate {
    public static final int CLASS_ID = -469536605;
    private TLAbsMessage message;
    private int pts;
    private int ptsCount;

    @Override
    public int getClassId() {
        return -469536605;
    }

    public TLAbsMessage getMessage() {
        return this.message;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    @Override
    public int getPtsCount() {
        return this.ptsCount;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.message, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.message = (TLAbsMessage)StreamingUtils.readTLObject(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateEditMessage#e40370a3";
    }
}

