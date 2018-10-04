
package org.telegram.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.codetype.TLAbsCodeType;
import org.telegram.api.auth.sentcodetype.TLAbsSentCodeType;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLSentCode
extends TLObject {
    public static final int CLASS_ID = 1577067778;
    private static final int FLAG_PHONE_REGISTERED = 1;
    private static final int FLAG_NEXT_TYPE = 2;
    private static final int FLAG_TIMEOUT = 4;
    private int flags;
    private TLAbsSentCodeType type;
    private String phoneCodeHash;
    private TLAbsCodeType nextType;
    private int timeout;

    @Override
    public int getClassId() {
        return 1577067778;
    }

    public String getPhoneCodeHash() {
        return this.phoneCodeHash;
    }

    public void setPhoneregfalse() {
        this.flags = 0;
    }

    public boolean isPhoneRegistered() {
        return (this.flags & 1) != 0;
    }

    public void setPhoneCodeHash(String phoneCodeHash) {
        this.phoneCodeHash = phoneCodeHash;
    }

    public TLAbsSentCodeType getType() {
        return this.type;
    }

    public void setType(TLAbsSentCodeType type) {
        this.type = type;
    }

    public TLAbsCodeType getNextType() {
        return this.nextType;
    }

    public void setNextType(TLAbsCodeType nextType) {
        this.nextType = nextType;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.type, stream);
        StreamingUtils.writeTLString(this.phoneCodeHash, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLObject(this.nextType, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeInt(this.timeout, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.type = (TLAbsSentCodeType)StreamingUtils.readTLObject(stream, context);
        this.phoneCodeHash = StreamingUtils.readTLString(stream);
        if ((this.flags & 2) != 0) {
            this.nextType = (TLAbsCodeType)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 4) != 0) {
            this.timeout = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "auth.sentCode#5e002502";
    }
}

