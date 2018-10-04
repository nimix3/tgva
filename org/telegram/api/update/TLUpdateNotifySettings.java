
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.notify.peer.TLAbsNotifyPeer;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateNotifySettings
extends TLAbsUpdate {
    public static final int CLASS_ID = -1094555409;
    private TLAbsNotifyPeer peer;
    private TLAbsPeerNotifySettings notifySettings;

    @Override
    public int getClassId() {
        return -1094555409;
    }

    public TLAbsNotifyPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsNotifyPeer peer) {
        this.peer = peer;
    }

    public TLAbsPeerNotifySettings getNotifySettings() {
        return this.notifySettings;
    }

    public void setNotifySettings(TLAbsPeerNotifySettings notifySettings) {
        this.notifySettings = notifySettings;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLObject(this.notifySettings, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsNotifyPeer)StreamingUtils.readTLObject(stream, context);
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateNotifySettings#bec268ef";
    }
}

