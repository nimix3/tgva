
package org.telegram.api.privacy.privacyrule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.privacy.privacyrule.TLAbsPrivacyRule;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLPrivacyValueAllowUsers
extends TLAbsPrivacyRule {
    public static final int CLASS_ID = 1297858060;
    protected TLVector<TLAbsUser> users = new TLVector();

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    @Override
    public int getClassId() {
        return 1297858060;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "privacy.PrivacyValueAllowUsers#4d5bbe0c";
    }
}

