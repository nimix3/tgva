
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountRegisterDevice
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 1147957548;
    private int tokenType;
    private String token;
    private String deviceModel;
    private String systemVersion;
    private String appVersion;
    private boolean appSandbox;
    private String langCode;

    @Override
    public int getClassId() {
        return 1147957548;
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

    public int getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(int value) {
        this.tokenType = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String value) {
        this.deviceModel = value;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String value) {
        this.systemVersion = value;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String value) {
        this.appVersion = value;
    }

    public boolean getAppSandbox() {
        return this.appSandbox;
    }

    public void setAppSandbox(boolean value) {
        this.appSandbox = value;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String value) {
        this.langCode = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.tokenType, stream);
        StreamingUtils.writeTLString(this.token, stream);
        StreamingUtils.writeTLString(this.deviceModel, stream);
        StreamingUtils.writeTLString(this.systemVersion, stream);
        StreamingUtils.writeTLString(this.appVersion, stream);
        StreamingUtils.writeTLBool(this.appSandbox, stream);
        StreamingUtils.writeTLString(this.langCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.tokenType = StreamingUtils.readInt(stream);
        this.token = StreamingUtils.readTLString(stream);
        this.deviceModel = StreamingUtils.readTLString(stream);
        this.systemVersion = StreamingUtils.readTLString(stream);
        this.appVersion = StreamingUtils.readTLString(stream);
        this.appSandbox = StreamingUtils.readTLBool(stream);
        this.langCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "account.registerDevice#446c712c";
    }
}

