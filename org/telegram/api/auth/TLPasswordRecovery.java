
package org.telegram.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLPasswordRecovery
extends TLObject {
    public static final int CLASS_ID = 326715557;
    private String emailPattern;

    @Override
    public int getClassId() {
        return 326715557;
    }

    public String getEmailPattern() {
        return this.emailPattern;
    }

    public void setEmailPattern(String emailPattern) {
        this.emailPattern = emailPattern;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.emailPattern, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.emailPattern = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.passwordrecovery#137948a5";
    }
}

