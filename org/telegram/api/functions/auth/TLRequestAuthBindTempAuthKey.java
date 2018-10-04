
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthBindTempAuthKey
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -841733627;
    private long permAuthKeyId;
    private long nonce;
    private int expiresAt;
    private TLBytes encryptedMessage;

    @Override
    public int getClassId() {
        return -841733627;
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
        StreamingUtils.writeLong(this.permAuthKeyId, stream);
        StreamingUtils.writeLong(this.nonce, stream);
        StreamingUtils.writeInt(this.expiresAt, stream);
        StreamingUtils.writeTLBytes(this.encryptedMessage, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.permAuthKeyId = StreamingUtils.readLong(stream);
        this.nonce = StreamingUtils.readLong(stream);
        this.expiresAt = StreamingUtils.readInt(stream);
        this.encryptedMessage = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "auth.bindTempAuthKey#cdd42a05";
    }
}

