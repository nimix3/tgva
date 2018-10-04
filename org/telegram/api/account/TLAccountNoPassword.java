
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAbsAccountPassword;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLAccountNoPassword
extends TLAbsAccountPassword {
    public static final int CLASS_ID = -1764049896;

    @Override
    public int getClassId() {
        return -1764049896;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.newSalt, stream);
        StreamingUtils.writeTLString(this.emailUnconfirmedPattern, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.newSalt = StreamingUtils.readTLBytes(stream, context);
        this.emailUnconfirmedPattern = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "accountNoPassword#96dabc18";
    }
}

