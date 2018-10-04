
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAbsAccountPassword;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLAccountPassword
extends TLAbsAccountPassword {
    public static final int CLASS_ID = 2081952796;
    private TLBytes currentSalt;
    private String hint = "";
    private boolean hasRecovery;

    @Override
    public int getClassId() {
        return 2081952796;
    }

    public TLBytes getCurrentSalt() {
        return this.currentSalt;
    }

    public void setCurrentSalt(TLBytes currentSalt) {
        this.currentSalt = currentSalt;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isHasRecovery() {
        return this.hasRecovery;
    }

    public void setHasRecovery(boolean hasRecovery) {
        this.hasRecovery = hasRecovery;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.currentSalt, stream);
        StreamingUtils.writeTLBytes(this.newSalt, stream);
        StreamingUtils.writeTLString(this.hint, stream);
        StreamingUtils.writeTLBool(this.hasRecovery, stream);
        StreamingUtils.writeTLString(this.emailUnconfirmedPattern, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.currentSalt = StreamingUtils.readTLBytes(stream, context);
        this.newSalt = StreamingUtils.readTLBytes(stream, context);
        this.hint = StreamingUtils.readTLString(stream);
        this.hasRecovery = StreamingUtils.readTLBool(stream);
        this.emailUnconfirmedPattern = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "accountPassword#7c18141c";
    }
}

