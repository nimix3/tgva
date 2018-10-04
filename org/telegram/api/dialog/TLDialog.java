
package org.telegram.api.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.dialog.TLAbsDialog;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDialog
extends TLAbsDialog {
    public static final int CLASS_ID = -1042448310;

    @Override
    public int getClassId() {
        return -1042448310;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.topMessage, stream);
        StreamingUtils.writeInt(this.readInboxMaxId, stream);
        StreamingUtils.writeInt(this.unreadCount, stream);
        StreamingUtils.writeTLObject(this.notifySettings, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        this.topMessage = StreamingUtils.readInt(stream);
        this.readInboxMaxId = StreamingUtils.readInt(stream);
        this.unreadCount = StreamingUtils.readInt(stream);
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "dialog.TLDialog#c1dd804a";
    }
}

