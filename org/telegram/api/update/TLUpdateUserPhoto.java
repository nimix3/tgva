
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateUserPhoto
extends TLAbsUpdate {
    public static final int CLASS_ID = -1791935732;
    private int userId;
    private boolean previous;
    private TLAbsUserProfilePhoto photo;
    private int date;

    @Override
    public int getClassId() {
        return -1791935732;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPrevious() {
        return this.previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public TLAbsUserProfilePhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsUserProfilePhoto photo) {
        this.photo = photo;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLBool(this.previous, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.photo = (TLAbsUserProfilePhoto)StreamingUtils.readTLObject(stream, context);
        this.previous = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "updateUserPhoto#95313b0c";
    }
}

