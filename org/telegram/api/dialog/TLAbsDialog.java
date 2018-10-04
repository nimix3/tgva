
package org.telegram.api.dialog;

import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.tl.TLObject;

public abstract class TLAbsDialog
extends TLObject {
    protected TLAbsPeer peer;
    protected int topMessage;
    protected int readInboxMaxId;
    protected int unreadCount;
    protected TLAbsPeerNotifySettings notifySettings;

    public TLAbsPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsPeer peer) {
        this.peer = peer;
    }

    public int getTopMessage() {
        return this.topMessage;
    }

    public void setTopMessage(int topMessage) {
        this.topMessage = topMessage;
    }

    public int getReadInboxMaxId() {
        return this.readInboxMaxId;
    }

    public void setReadInboxMaxId(int readInboxMaxId) {
        this.readInboxMaxId = readInboxMaxId;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public TLAbsPeerNotifySettings getNotifySettings() {
        return this.notifySettings;
    }

    public void setNotifySettings(TLAbsPeerNotifySettings notifySettings) {
        this.notifySettings = notifySettings;
    }
}

