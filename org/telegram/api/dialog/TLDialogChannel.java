
package org.telegram.api.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.dialog.TLAbsDialog;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDialogChannel
extends TLAbsDialog {
    public static final int CLASS_ID = 1535415986;
    private int topImportantMessage;
    private int unreadImportantCount;
    private int pts;

    @Override
    public int getClassId() {
        return 1535415986;
    }

    @Override
    public TLAbsPeer getPeer() {
        return this.peer;
    }

    @Override
    public void setPeer(TLAbsPeer value) {
        this.peer = value;
    }

    @Override
    public int getTopMessage() {
        return this.topMessage;
    }

    @Override
    public void setTopMessage(int value) {
        this.topMessage = value;
    }

    @Override
    public int getUnreadCount() {
        return this.unreadCount;
    }

    @Override
    public void setUnreadCount(int value) {
        this.unreadCount = value;
    }

    @Override
    public TLAbsPeerNotifySettings getNotifySettings() {
        return this.notifySettings;
    }

    @Override
    public void setNotifySettings(TLAbsPeerNotifySettings notifySettings) {
        this.notifySettings = notifySettings;
    }

    @Override
    public int getReadInboxMaxId() {
        return this.readInboxMaxId;
    }

    @Override
    public void setReadInboxMaxId(int readInboxMaxId) {
        this.readInboxMaxId = readInboxMaxId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.topMessage, stream);
        StreamingUtils.writeInt(this.topImportantMessage, stream);
        StreamingUtils.writeInt(this.readInboxMaxId, stream);
        StreamingUtils.writeInt(this.unreadCount, stream);
        StreamingUtils.writeInt(this.unreadImportantCount, stream);
        StreamingUtils.writeTLObject(this.notifySettings, stream);
        StreamingUtils.writeInt(this.pts, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        this.topMessage = StreamingUtils.readInt(stream);
        this.topImportantMessage = StreamingUtils.readInt(stream);
        this.readInboxMaxId = StreamingUtils.readInt(stream);
        this.unreadCount = StreamingUtils.readInt(stream);
        this.unreadImportantCount = StreamingUtils.readInt(stream);
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
        this.pts = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "dialog.TLDialogChannel#5b8496b2";
    }
}

