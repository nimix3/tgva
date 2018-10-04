
package org.telegram.api.encrypted.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLEncryptedChatWaiting
extends TLAbsEncryptedChat {
    public static final int CLASS_ID = 1006044124;
    private long accessHash;
    private int date;
    private int adminId;
    private int participantId;

    @Override
    public int getClassId() {
        return 1006044124;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getParticipantId() {
        return this.participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.adminId, stream);
        StreamingUtils.writeInt(this.participantId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
        this.adminId = StreamingUtils.readInt(stream);
        this.participantId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "encryptedChatWaiting#3bf703dc";
    }
}

