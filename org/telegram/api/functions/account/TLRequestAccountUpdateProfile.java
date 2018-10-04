
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountUpdateProfile
extends TLMethod<TLAbsUser> {
    public static final int CLASS_ID = 2018596725;
    private static final int FLAG_FIRSTNAME = 1;
    private static final int FLAG_LASTNAME = 2;
    private static final int FLAG_ABOUT = 4;
    private int flags;
    private String firstName;
    private String lastName;
    private String about;

    @Override
    public int getClassId() {
        return 2018596725;
    }

    @Override
    public TLAbsUser deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUser) {
            return (TLAbsUser)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUser.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
        this.enableFlag(1, value != null && !value.isEmpty());
    }

    private void enableFlag(int flag, boolean enabled) {
        this.flags = enabled ? (this.flags |= flag) : (this.flags &= ~ flag);
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
        this.enableFlag(2, value != null && !value.isEmpty());
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String value) {
        this.about = value;
        this.enableFlag(4, value != null);
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLString(this.firstName, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.lastName, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.about, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.firstName = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 2) != 0) {
            this.lastName = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.about = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "account.updateProfile#78515775";
    }
}

