
package org.telegram.api.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLSupport
extends TLObject {
    public static final int CLASS_ID = 398898678;
    private String phoneNumber;
    private TLAbsUser user;

    @Override
    public int getClassId() {
        return 398898678;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    public TLAbsUser getUser() {
        return this.user;
    }

    public void setUser(TLAbsUser value) {
        this.user = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLObject(this.user, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.user = (TLAbsUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "help.support#17c6b5f6";
    }
}

