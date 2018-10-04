
package org.telegram.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.TLBotInfo;
import org.telegram.api.contacts.TLContactsLink;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLUserFull
extends TLObject {
    public static final int CLASS_ID = 1496513539;
    private static final int FLAG_BLOCKED = 1;
    private static final int FLAG_ABOUT = 2;
    private static final int FLAG_PROFILE_PHOTO = 4;
    private static final int FLAG_BOT_INFO = 8;
    private int flags;
    private TLAbsUser user;
    private String about;
    private TLContactsLink link;
    private TLAbsPhoto profilePhoto;
    private TLAbsPeerNotifySettings notifySettings;
    private TLBotInfo botInfo;

    @Override
    public int getClassId() {
        return 1496513539;
    }

    public TLAbsUser getUser() {
        return this.user;
    }

    public void setUser(TLAbsUser value) {
        this.user = value;
    }

    public TLContactsLink getLink() {
        return this.link;
    }

    public void setLink(TLContactsLink value) {
        this.link = value;
    }

    public TLAbsPhoto getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setProfilePhoto(TLAbsPhoto value) {
        this.profilePhoto = value;
    }

    public TLAbsPeerNotifySettings getNotifySettings() {
        return this.notifySettings;
    }

    public void setNotifySettings(TLAbsPeerNotifySettings value) {
        this.notifySettings = value;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public TLBotInfo getBotInfo() {
        return this.botInfo;
    }

    public void setBotInfo(TLBotInfo botInfo) {
        this.botInfo = botInfo;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.user, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.about, stream);
        }
        StreamingUtils.writeTLObject(this.link, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.profilePhoto, stream);
        }
        StreamingUtils.writeTLObject(this.notifySettings, stream);
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLObject(this.botInfo, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.user = (TLAbsUser)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 2) != 0) {
            this.about = StreamingUtils.readTLString(stream);
        }
        this.link = (TLContactsLink)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 4) != 0) {
            this.profilePhoto = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        }
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 8) != 0) {
            this.botInfo = (TLBotInfo)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "userFull#5932fc03";
    }
}

