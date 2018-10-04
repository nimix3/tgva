
package org.telegram.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAuthorization
extends TLObject {
    public static final int CLASS_ID = -16553231;
    private TLAbsUser user;

    @Override
    public int getClassId() {
        return -16553231;
    }

    public TLAbsUser getUser() {
        return this.user;
    }

    public void setUser(TLAbsUser value) {
        this.user = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.user, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.user = (TLAbsUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "auth.authorization#ff036af1";
    }
}

