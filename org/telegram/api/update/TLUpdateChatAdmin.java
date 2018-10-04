
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChatAdmin
extends TLAbsUpdate {
    public static final int CLASS_ID = 1855224129;
    private int chatId;
    private boolean enabled;
    private int version;

    @Override
    public int getClassId() {
        return 1855224129;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLBool(this.enabled, stream);
        StreamingUtils.writeInt(this.version, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.enabled = StreamingUtils.readTLBool(stream);
        this.version = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "update.TLUpdateChatAdmin#6e947941";
    }
}

