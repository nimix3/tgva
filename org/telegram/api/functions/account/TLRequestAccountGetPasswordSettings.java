
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountPasswordSettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetPasswordSettings
extends TLMethod<TLAccountPasswordSettings> {
    public static final int CLASS_ID = -1131605573;
    private TLBytes currentPasswordHash;

    @Override
    public int getClassId() {
        return -1131605573;
    }

    @Override
    public TLAccountPasswordSettings deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAccountPasswordSettings) {
            return (TLAccountPasswordSettings)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLAccountPasswordSettings, got: " + res.getClass().getCanonicalName());
    }

    public TLBytes getCurrentPasswordHash() {
        return this.currentPasswordHash;
    }

    public void setCurrentPasswordHash(TLBytes currentPasswordHash) {
        this.currentPasswordHash = currentPasswordHash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.currentPasswordHash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.currentPasswordHash = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "account.getPasswordSettings#bc8d11bb";
    }
}

