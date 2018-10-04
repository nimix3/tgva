
package org.telegram.api.functions.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLSentCode;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAuthSendCode
extends TLMethod<TLSentCode> {
    public static final int CLASS_ID = -855805745;
    private static final int FLAG_ALLOW_FLASHCALL = 1;
    private int flags;
    private String phoneNumber;
    private Boolean currentPhoneNumber;
    private int apiId;
    private String apiHash;
    private String langCode;

    public void setallowflashcall(boolean allowFlashcall) {
        this.flags = 0;
        int n = this.flags = allowFlashcall ? this.flags | 1 : this.flags & -2;
        if ((this.flags & 1) == 0) {
            this.currentPhoneNumber = null;
        }
    }

    @Override
    public int getClassId() {
        return -855805745;
    }

    @Override
    public TLSentCode deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLSentCode) {
            return (TLSentCode)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.auth.TLSentCode, got: " + res.getClass().getCanonicalName());
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    public int getApiId() {
        return this.apiId;
    }

    public void setApiId(int value) {
        this.apiId = value;
    }

    public String getApiHash() {
        return this.apiHash;
    }

    public void setApiHash(String value) {
        this.apiHash = value;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String value) {
        this.langCode = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLBool(this.currentPhoneNumber, stream);
        }
        StreamingUtils.writeInt(this.apiId, stream);
        StreamingUtils.writeTLString(this.apiHash, stream);
        StreamingUtils.writeTLString(this.langCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.phoneNumber = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.currentPhoneNumber = StreamingUtils.readTLBool(stream);
        }
        this.apiId = StreamingUtils.readInt(stream);
        this.apiHash = StreamingUtils.readTLString(stream);
        this.langCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "auth.sendCode#ccfd70cf";
    }
}

