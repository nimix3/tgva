
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChatParticipantAdd
extends TLAbsUpdate {
    public static final int CLASS_ID = -364179876;
    private int chatId;
    private int userId;
    private int inviterId;
    private int date;
    private int version;

    @Override
    public int getClassId() {
        return -364179876;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getInviterId() {
        return this.inviterId;
    }

    public void setInviterId(int inviterId) {
        this.inviterId = inviterId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeInt(this.inviterId, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.version, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.inviterId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.version = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateChatParticipantAdd#ea4b0e5c";
    }
}

