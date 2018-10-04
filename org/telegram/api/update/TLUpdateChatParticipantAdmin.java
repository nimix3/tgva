
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChatParticipantAdmin
extends TLAbsUpdate {
    public static final int CLASS_ID = -1232070311;
    private int chatId;
    private int userId;
    private boolean isAdmin;
    private int version;

    @Override
    public int getClassId() {
        return -1232070311;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
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
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLBool(this.isAdmin, stream);
        StreamingUtils.writeInt(this.version, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.isAdmin = StreamingUtils.readTLBool(stream);
        this.version = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "update.TLUpdateChatParticipantAdmin#b6901959";
    }
}

