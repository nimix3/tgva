
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAccountPasswordSettings
extends TLObject {
    public static final int CLASS_ID = -1212732749;
    private String email;

    @Override
    public int getClassId() {
        return -1212732749;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.email, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.email = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "accountPasswordSettings#b7b72ab3";
    }
}

