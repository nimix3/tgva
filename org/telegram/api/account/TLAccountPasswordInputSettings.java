
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAccountPasswordInputSettings
extends TLObject {
    public static final int CLASS_ID = -2037289493;
    private static final int FLAG_SALT = 1;
    private static final int FLAG_EMAIL = 2;
    private int flags;
    private TLBytes newSalt;
    private TLBytes newPasswordHash;
    private String hint;
    private String email;

    @Override
    public int getClassId() {
        return -2037289493;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLBytes getNewSalt() {
        return this.newSalt;
    }

    public void setNewSalt(TLBytes newSalt) {
        this.newSalt = newSalt;
    }

    public TLBytes getNewPasswordHash() {
        return this.newPasswordHash;
    }

    public void setNewPasswordHash(TLBytes newPasswordHash) {
        this.newPasswordHash = newPasswordHash;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLBytes(this.newSalt, stream);
            StreamingUtils.writeTLBytes(this.newPasswordHash, stream);
            StreamingUtils.writeTLString(this.hint, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.email, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.newSalt = StreamingUtils.readTLBytes(stream, context);
            this.newPasswordHash = StreamingUtils.readTLBytes(stream, context);
            this.hint = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 2) != 0) {
            this.email = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "accountPasswordInputSettings#86916deb";
    }
}

