
package org.telegram.api.updates.difference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDifferenceEmpty
extends TLAbsDifference {
    public static final int CLASS_ID = 1567990072;

    @Override
    public int getClassId() {
        return 1567990072;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.seq, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.date = StreamingUtils.readInt(stream);
        this.seq = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updates.differenceEmpty#5d75a138";
    }
}

