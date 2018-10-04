
package org.telegram.api.updates.channel.differences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.channel.differences.TLAbsUpdatesChannelDifferences;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdatesChannelDifferencesEmpty
extends TLAbsUpdatesChannelDifferences {
    public static final int CLASS_ID = 1041346555;
    private static final int FLAG_FINAL = 1;
    private static final int FLAG_TIMEOUT = 2;

    @Override
    public int getClassId() {
        return 1041346555;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.pts, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeInt(this.timeout, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.pts = StreamingUtils.readInt(stream);
        if ((this.flags & 2) != 0) {
            this.timeout = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "updates.TLUpdatesChannelDifferencesEmpty#3e11affb";
    }
}

