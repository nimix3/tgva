
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountPasswordInputSettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountUpdatePasswordSettings
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -92517498;
    private TLBytes currentPasswordHash;
    private TLAccountPasswordInputSettings newSettings;

    @Override
    public int getClassId() {
        return -92517498;
    }

    public TLBytes getCurrentPasswordHash() {
        return this.currentPasswordHash;
    }

    public void setCurrentPasswordHash(TLBytes currentPasswordHash) {
        this.currentPasswordHash = currentPasswordHash;
    }

    public TLAccountPasswordInputSettings getNewSettings() {
        return this.newSettings;
    }

    public void setNewSettings(TLAccountPasswordInputSettings newSettings) {
        this.newSettings = newSettings;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.currentPasswordHash, stream);
        StreamingUtils.writeTLObject(this.newSettings, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.currentPasswordHash = StreamingUtils.readTLBytes(stream, context);
        this.newSettings = (TLAccountPasswordInputSettings)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.updatePasswordSettings#fa7c4b86";
    }
}

