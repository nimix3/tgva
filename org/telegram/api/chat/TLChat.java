
package org.telegram.api.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.photo.TLAbsChatPhoto;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChat
extends TLAbsChat {
    public static final int CLASS_ID = -652419756;
    private static final int FLAG_USER_CREATOR = 1;
    private static final int FLAG_USER_KICKED = 2;
    private static final int FLAG_USER_LEFT = 4;
    private static final int FLAG_ADMINS_ENABLED = 8;
    private static final int FLAG_USER_ADMIN = 16;
    private static final int FLAG_DEACTIVATED = 32;
    private static final int FLAG_MIGRATED_TO = 64;
    private int flags;
    private String title;
    private TLAbsChatPhoto photo;
    private int participantsCount;
    private int date;
    private int version;
    private TLAbsInputChannel migratedTo;

    @Override
    public int getClassId() {
        return -652419756;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public TLAbsChatPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsChatPhoto photo) {
        this.photo = photo;
    }

    public int getParticipantsCount() {
        return this.participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public TLAbsInputChannel getMigratedTo() {
        return this.migratedTo;
    }

    public void setMigratedTo(TLAbsInputChannel migratedTo) {
        this.migratedTo = migratedTo;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public boolean isForbidden() {
        return (this.flags & 2) != 0 || (this.flags & 4) != 0;
    }

    public boolean isMigratedTo() {
        return (this.flags & 64) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeInt(this.participantsCount, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.version, stream);
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeTLObject(this.migratedTo, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.photo = (TLAbsChatPhoto)StreamingUtils.readTLObject(stream, context);
        this.participantsCount = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.version = StreamingUtils.readInt(stream);
        if ((this.flags & 64) != 0) {
            this.migratedTo = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "chat#d91cdd54";
    }
}

