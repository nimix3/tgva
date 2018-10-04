
package org.telegram.api.chat.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.photo.TLAbsChatPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChannel
extends TLAbsChat {
    public static final int CLASS_ID = -1588737454;
    private static final int FLAG_CREATOR = 1;
    private static final int FLAG_KICKED = 2;
    private static final int FLAG_LEFT = 4;
    private static final int FLAG_EDITOR = 8;
    private static final int FLAG_MODERATOR = 16;
    private static final int FLAG_BROADCAST = 32;
    private static final int FLAG_USERNAME = 64;
    private static final int FLAG_VERIFIED = 128;
    private static final int FLAG_MEGAGROUP = 256;
    private static final int FLAG_RESTRICTED = 512;
    private static final int FLAG_INVITES_ENABLED = 1024;
    private static final int FLAG_SIGNATURES = 2048;
    private static final int FLAG_MIN = 4096;
    private static final int FLAG_ACCESS_HASH = 8192;
    private int flags;
    private long accessHash;
    private String title;
    private String username;
    private TLAbsChatPhoto photo;
    private int date;
    private int version;
    private String restrictionReason;

    @Override
    public int getClassId() {
        return -1588737454;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TLAbsChatPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsChatPhoto photo) {
        this.photo = photo;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestrictionReason() {
        return this.restrictionReason;
    }

    public void setRestrictionReason(String restrictionReason) {
        this.restrictionReason = restrictionReason;
    }

    public boolean hasUsername() {
        return (this.flags & 64) != 0;
    }

    public boolean hasRestrictionReason() {
        return (this.flags & 512) != 0;
    }

    public boolean hasAccessHash() {
        return (this.flags & 8192) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        if ((this.flags & 8192) != 0) {
            StreamingUtils.writeLong(this.accessHash, stream);
        }
        StreamingUtils.writeTLString(this.title, stream);
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeTLString(this.username, stream);
        }
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.version, stream);
        if ((this.flags & 512) != 0) {
            StreamingUtils.writeTLString(this.restrictionReason, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        if ((this.flags & 8192) != 0) {
            this.accessHash = StreamingUtils.readLong(stream);
        }
        this.title = StreamingUtils.readTLString(stream);
        if ((this.flags & 64) != 0) {
            this.username = StreamingUtils.readTLString(stream);
        }
        this.photo = (TLAbsChatPhoto)StreamingUtils.readTLObject(stream, context);
        this.date = StreamingUtils.readInt(stream);
        this.version = StreamingUtils.readInt(stream);
        if ((this.flags & 512) != 0) {
            this.restrictionReason = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "channel.TLChannel#a14dca52";
    }
}

