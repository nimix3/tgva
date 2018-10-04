
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.privacy.privacyrule.TLAbsPrivacyRule;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLAccountPrivacyRules
extends TLObject {
    public static final int CLASS_ID = 1430961007;
    private TLVector<TLAbsPrivacyRule> rules;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return 1430961007;
    }

    public TLVector<TLAbsPrivacyRule> getRules() {
        return this.rules;
    }

    public void setRules(TLVector<TLAbsPrivacyRule> rules) {
        this.rules = rules;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.rules, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.rules = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "account.PrivacyRules#554abb6f";
    }
}

