
package org.telegram.api.notify.peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.notify.peer.TLAbsNotifyPeer;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLNotifyPeer
extends TLAbsNotifyPeer {
    public static final int CLASS_ID = -1613493288;
    private TLAbsPeer peer;

    @Override
    public int getClassId() {
        return -1613493288;
    }

    public TLAbsPeer getPeer() {
        return this.peer;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "notifyPeer#9fd40bd8";
    }
}

