
package org.telegram.api.input.peer.notify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputPeerNotifySettings
extends TLObject {
    public static final int CLASS_ID = 949182130;
    private static final int FLAG_SHOW_PREVIEWS = 1;
    private static final int FLAG_SILENT = 2;
    private int flags;
    private int muteUntil;
    private String sound;

    @Override
    public int getClassId() {
        return 949182130;
    }

    public int getMuteUntil() {
        return this.muteUntil;
    }

    public void setMuteUntil(int value) {
        this.muteUntil = value;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String value) {
        this.sound = value;
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
        return "inputPeerNotifySettings#38935eb2";
    }
}

