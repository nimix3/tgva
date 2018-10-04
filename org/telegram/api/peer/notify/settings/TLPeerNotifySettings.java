
package org.telegram.api.peer.notify.settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLPeerNotifySettings
extends TLAbsPeerNotifySettings {
    public static final int CLASS_ID = -1697798976;
    private static final int FLAG_SHOW_PREVIEWS = 1;
    private static final int FLAG_SILENT = 2;
    private int flags;
    private int muteUntil;
    private String sound;

    public int getMuteUntil() {
        return this.muteUntil;
    }

    public void setMuteUntil(int muteUntil) {
        this.muteUntil = muteUntil;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public int getClassId() {
        return -1697798976;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.muteUntil, stream);
        StreamingUtils.writeTLString(this.sound, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.muteUntil = StreamingUtils.readInt(stream);
        this.sound = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "peerNotifySettings#9acda4c0";
    }
}

