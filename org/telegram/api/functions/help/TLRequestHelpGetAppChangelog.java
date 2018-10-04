
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.changelog.TLAbsAppChangelog;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetAppChangelog
extends TLMethod<TLAbsAppChangelog> {
    public static final int CLASS_ID = 1537966002;
    private String deviceModel;
    private String systemVersion;
    private String appVersion;
    private String langCode;

    @Override
    public int getClassId() {
        return 1537966002;
    }

    @Override
    public TLAbsAppChangelog deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsAppChangelog) {
            return (TLAbsAppChangelog)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsAppChangelog.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
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
        return "functions.help.TLRequestHelpGetAppChangelog#5bab7fb2";
    }
}

