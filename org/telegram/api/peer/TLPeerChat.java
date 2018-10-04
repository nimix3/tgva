
package org.telegram.api.peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLPeerChat
extends TLAbsPeer {
    public static final int CLASS_ID = -1160714821;

    @Override
    public int getClassId() {
        return -1160714821;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "peerChat#bad0e5bb";
    }
}

