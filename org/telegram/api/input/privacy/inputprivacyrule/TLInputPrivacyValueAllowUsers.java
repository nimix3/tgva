
package org.telegram.api.input.privacy.inputprivacyrule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.privacy.inputprivacyrule.TLAbsInputPrivacyRule;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLInputPrivacyValueAllowUsers
extends TLAbsInputPrivacyRule {
    public static final int CLASS_ID = 320652927;
    protected TLVector<TLAbsUser> users = new TLVector();

    @Override
    public int getClassId() {
        return 320652927;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
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
        return "inputPrivacyValueAllowUsers#131cc67f";
    }
}

