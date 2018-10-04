
package org.telegram.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto;
import org.telegram.api.user.status.TLAbsUserStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUser
extends TLAbsUser {
    public static final int CLASS_ID = -787638374;
    private static final int FLAG_ACCESS_HASH = 1;
    private static final int FLAG_FIRST_NAME = 2;
    private static final int FLAG_LAST_NAME = 4;
    private static final int FLAG_USERNAME = 8;
    private static final int FLAG_PHONE = 16;
    private static final int FLAG_PHOTO = 32;
    private static final int FLAG_STATUS = 64;
    private static final int FLAG_UNUSED = 128;
    private static final int FLAG_UNUSED2 = 256;
    private static final int FLAG_UNUSED3 = 512;
    private static final int FLAG_SELF = 1024;
    private static final int FLAG_CONTACT = 2048;
    private static final int FLAG_MUTUAL_CONTACT = 4096;
    private static final int FLAG_DELETED = 8192;
    private static final int FLAG_BOT = 16384;
    private static final int FLAG_BOT_READING_HISTORY = 32768;
    private static final int FLAG_BOT_CANT_JOIN_GROUP = 65536;
    private static final int FLAG_VERIFIED = 131072;
    private static final int FLAG_RESTRICTED = 262144;
    private static final int FLAG_INLINE_PLACEHOLDER = 524288;
    private static final int FLAG_MIN = 1048576;
    private static final int FLAG_BOT_INLINE_GEO = 2097152;
    private int flags;
    private long accessHash;
    private String firstName = "";
    private String lastName = "";
    private String userName = "";
    private String phone = "";
    private TLAbsUserProfilePhoto photo;
    private TLAbsUserStatus status;
    private int botInfoVersion;
    private String restrictionReason;
    private String botInlinePlaceholder;

    @Override
    public int getClassId() {
        return -787638374;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TLAbsUserProfilePhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsUserProfilePhoto photo) {
        this.photo = photo;
    }

    public TLAbsUserStatus getStatus() {
        return this.status;
    }

    public void setStatus(TLAbsUserStatus status) {
        this.status = status;
    }

    public int getBotInfoVersion() {
        return this.botInfoVersion;
    }

    public void setBotInfoVersion(int botInfoVersion) {
        this.botInfoVersion = botInfoVersion;
    }

    public String getRestrictionReason() {
        return this.restrictionReason;
    }

    public void setRestrictionReason(String restrictionReason) {
        this.restrictionReason = restrictionReason;
    }

    public String getBotInlinePlaceholder() {
        return this.botInlinePlaceholder;
    }

    public void setBotInlinePlaceholder(String botInlinePlaceholder) {
        this.botInlinePlaceholder = botInlinePlaceholder;
    }

    public boolean isSelf() {
        return (this.flags & 1024) != 0;
    }

    public boolean isContact() {
        return (this.flags & 2048) != 0 || this.isMutualContact();
    }

    public boolean isMutualContact() {
        return (this.flags & 4096) != 0;
    }

    public boolean isDeleted() {
        return (this.flags & 8192) != 0;
    }

    public boolean isBot() {
        return (this.flags & 16384) != 0;
    }

    public boolean isBotReadingHistory() {
        return (this.flags & 32768) != 0;
    }

    public boolean isBotCantAddToGroup() {
        return (this.flags & 65536) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeLong(this.accessHash, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.firstName, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.lastName, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLString(this.userName, stream);
        }
        if ((this.flags & 16) != 0) {
            StreamingUtils.writeTLString(this.phone, stream);
        }
        if ((this.flags & 32) != 0) {
            StreamingUtils.writeTLObject(this.photo, stream);
        }
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeTLObject(this.status, stream);
        }
        if ((this.flags & 16384) != 0) {
            StreamingUtils.writeInt(this.botInfoVersion, stream);
        }
        if ((this.flags & 262144) != 0) {
            StreamingUtils.writeTLString(this.restrictionReason, stream);
        }
        if ((this.flags & 524288) != 0) {
            StreamingUtils.writeTLString(this.botInlinePlaceholder, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.accessHash = StreamingUtils.readLong(stream);
        }
        if ((this.flags & 2) != 0) {
            this.firstName = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.lastName = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 8) != 0) {
            this.userName = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 16) != 0) {
            this.phone = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 32) != 0) {
            this.photo = (TLAbsUserProfilePhoto)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 64) != 0) {
            this.status = (TLAbsUserStatus)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 16384) != 0) {
            this.botInfoVersion = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 262144) != 0) {
            this.restrictionReason = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 524288) != 0) {
            this.botInlinePlaceholder = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "user.TLUser#d10d979a";
    }
}

