
package org.telegram.api.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateShort
extends TLAbsUpdates {
    public static final int CLASS_ID = 2027216577;
    private TLAbsUpdate update;
    private int date;

    public TLAbsUpdate getUpdate() {
        return this.update;
    }

    public void setUpdate(TLAbsUpdate update) {
        this.update = update;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int getClassId() {
        return 2027216577;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.update, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.update = (TLAbsUpdate)StreamingUtils.readTLObject(stream, context);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateShort#78d4dec1";
    }
}

