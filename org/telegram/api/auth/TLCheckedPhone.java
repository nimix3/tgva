
package org.telegram.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLCheckedPhone
extends TLObject {
    public static final int CLASS_ID = -2128698738;
    private boolean phoneRegistered;

    @Override
    public int getClassId() {
        return -2128698738;
    }

    public boolean isPhoneRegistered() {
        return this.phoneRegistered;
    }

    public void setPhoneRegistered(boolean phoneRegistered) {
        this.phoneRegistered = phoneRegistered;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBool(this.phoneRegistered, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.phoneRegistered = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "auth.checkedPhone#811ea28e";
    }
}

