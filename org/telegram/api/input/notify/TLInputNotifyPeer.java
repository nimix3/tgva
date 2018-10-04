
package org.telegram.api.input.notify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.notify.TLAbsInputNotifyPeer;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputNotifyPeer
extends TLAbsInputNotifyPeer {
    public static final int CLASS_ID = -1195615476;
    private TLAbsInputPeer peer;

    @Override
    public int getClassId() {
        return -1195615476;
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputNotifyPeer#b8bc5b0c";
    }
}

