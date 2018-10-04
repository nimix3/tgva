
package org.telegram.api.chat;

import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.TLObject;

public abstract class TLAbsChatFull
extends TLObject {
    protected int id;
    protected TLAbsPhoto photo;
    protected TLAbsPeerNotifySettings notifySettings;
    protected TLAbsChatInvite exportedInvite;

    protected TLAbsChatFull() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsPhoto photo) {
        this.photo = photo;
    }

    public TLAbsPeerNotifySettings getNotifySettings() {
        return this.notifySettings;
    }

    public void setNotifySettings(TLAbsPeerNotifySettings notifySettings) {
        this.notifySettings = notifySettings;
    }

    public TLAbsChatInvite getExportedInvite() {
        return this.exportedInvite;
    }

    public void setExportedInvite(TLAbsChatInvite exportedInvite) {
        this.exportedInvite = exportedInvite;
    }
}

