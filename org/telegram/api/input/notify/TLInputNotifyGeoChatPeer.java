
package org.telegram.api.input.notify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.TLInputGeoChat;
import org.telegram.api.input.notify.TLAbsInputNotifyPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputNotifyGeoChatPeer
extends TLAbsInputNotifyPeer {
    public static final int CLASS_ID = 1301143240;
    protected TLInputGeoChat peer;

    @Override
    public int getClassId() {
        return 1301143240;
    }

    public TLInputGeoChat getPeer() {
        return this.peer;
    }

    public void setPeer(TLInputGeoChat value) {
        this.peer = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLInputGeoChat)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputNotifyGeoChatPeer#4d8ddec8";
    }
}

