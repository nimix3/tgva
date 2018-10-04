
package org.telegram.api.chat.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.TLBotInfo;
import org.telegram.api.chat.TLAbsChatFull;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLChannelFull
extends TLAbsChatFull {
    public static final int CLASS_ID = -1749097118;
    private static final int FLAG_PARTICIPANTS = 1;
    private static final int FLAG_ADMIN = 2;
    private static final int FLAG_KICKED = 4;
    private static final int FLAG_CAN_VIEW_PARTICIPANTS = 8;
    private static final int FLAG_MIGRATED = 16;
    private static final int FLAG_PINNED_MESSAGE = 32;
    private static final int FLAG_CAN_SET_USERNAME = 64;
    private int flags;
    private String about;
    private int participantsCount;
    private int adminCount;
    private int kickedCount;
    private int readInboxMaxId;
    private int unreadCount;
    private int unreadImportantCout;
    private TLVector<TLBotInfo> botInfo;
    private int migratedFromChatId;
    private int migratedFromMaxId;
    private int pinnedMessageId;

    @Override
    public int getClassId() {
        return -1749097118;
    }

    public int getReadInboxMaxId() {
        return this.readInboxMaxId;
    }

    public void setReadInboxMaxId(int readInboxMaxId) {
        this.readInboxMaxId = readInboxMaxId;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadImportantCout() {
        return this.unreadImportantCout;
    }

    public void setUnreadImportantCout(int unreadImportantCout) {
        this.unreadImportantCout = unreadImportantCout;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getParticipantsCount() {
        return this.participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public int getAdminCount() {
        return this.adminCount;
    }

    public void setAdminCount(int adminCount) {
        this.adminCount = adminCount;
    }

    public int getKickedCount() {
        return this.kickedCount;
    }

    public void setKickedCount(int kickedCount) {
        this.kickedCount = kickedCount;
    }

    public TLVector<TLBotInfo> getBotInfo() {
        return this.botInfo;
    }

    public void setBotInfo(TLVector<TLBotInfo> botInfo) {
        this.botInfo = botInfo;
    }

    public int getMigratedFromChatId() {
        return this.migratedFromChatId;
    }

    public void setMigratedFromChatId(int migratedFromChatId) {
        this.migratedFromChatId = migratedFromChatId;
    }

    public int getMigratedFromMaxId() {
        return this.migratedFromMaxId;
    }

    public void setMigratedFromMaxId(int migratedFromMaxId) {
        this.migratedFromMaxId = migratedFromMaxId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.about, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.participantsCount, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeInt(this.adminCount, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeInt(this.kickedCount, stream);
        }
        StreamingUtils.writeInt(this.readInboxMaxId, stream);
        StreamingUtils.writeInt(this.unreadCount, stream);
        StreamingUtils.writeInt(this.unreadImportantCout, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLObject(this.notifySettings, stream);
        StreamingUtils.writeTLObject(this.exportedInvite, stream);
        StreamingUtils.writeTLVector(this.botInfo, stream);
        if ((this.flags & 16) != 0) {
            StreamingUtils.writeInt(this.migratedFromChatId, stream);
            StreamingUtils.writeInt(this.migratedFromMaxId, stream);
        }
        if ((this.flags & 32) != 0) {
            StreamingUtils.writeInt(this.pinnedMessageId, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.about = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.participantsCount = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 2) != 0) {
            this.adminCount = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 4) != 0) {
            this.kickedCount = StreamingUtils.readInt(stream);
        }
        this.readInboxMaxId = StreamingUtils.readInt(stream);
        this.unreadCount = StreamingUtils.readInt(stream);
        this.unreadImportantCout = StreamingUtils.readInt(stream);
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
        this.exportedInvite = (TLAbsChatInvite)StreamingUtils.readTLObject(stream, context);
        this.botInfo = StreamingUtils.readTLVector(stream, context);
        if ((this.flags & 16) != 0) {
            this.migratedFromChatId = StreamingUtils.readInt(stream);
            this.migratedFromMaxId = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 32) != 0) {
            this.pinnedMessageId = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "channel.TLChannelFull#97bee562";
    }
}

