
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.TLAbsAppUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetAppUpdate
extends TLMethod<TLAbsAppUpdate> {
    public static final int CLASS_ID = -938300290;
    private String deviceModel;
    private String systemVersion;
    private String appVersion;
    private String langCode;

    @Override
    public int getClassId() {
        return -938300290;
    }

    @Override
    public TLAbsAppUpdate deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsAppUpdate) {
            return (TLAbsAppUpdate)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.help.TLAbsAppUpdate, got: " + res.getClass().getCanonicalName());
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

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String value) {
        this.langCode = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.deviceModel, stream);
        StreamingUtils.writeTLString(this.systemVersion, stream);
        StreamingUtils.writeTLString(this.appVersion, stream);
        StreamingUtils.writeTLString(this.langCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.deviceModel = StreamingUtils.readTLString(stream);
        this.systemVersion = StreamingUtils.readTLString(stream);
        this.appVersion = StreamingUtils.readTLString(stream);
        this.langCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "help.getAppUpdate#c812ac7e";
    }
}

