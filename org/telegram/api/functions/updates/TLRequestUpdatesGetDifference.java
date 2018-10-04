
package org.telegram.api.functions.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUpdatesGetDifference
extends TLMethod<TLAbsDifference> {
    public static final int CLASS_ID = 168039573;
    private int pts;
    private int date;
    private int qts;

    @Override
    public int getClassId() {
        return 168039573;
    }

    @Override
    public TLAbsDifference deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsDifference) {
            return (TLAbsDifference)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.updates.difference.TLAbsDifference, got: " + res.getClass().getCanonicalName());
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int value) {
        this.pts = value;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    public int getQts() {
        return this.qts;
    }

    public void setQts(int value) {
        this.qts = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.qts, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.pts = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.qts = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updates.getDifference#a041495";
    }
}

