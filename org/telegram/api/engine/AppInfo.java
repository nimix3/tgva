
package org.telegram.api.engine;

public class AppInfo {
    protected int apiId;
    protected String deviceModel;
    protected String systemVersion;
    protected String appVersion;
    protected String langCode;

    public AppInfo(int apiId, String deviceModel, String systemVersion, String appVersion, String langCode) {
        this.apiId = apiId;
        this.deviceModel = deviceModel;
        this.systemVersion = systemVersion;
        this.appVersion = appVersion;
        this.langCode = langCode;
    }

    public int getApiId() {
        return this.apiId;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getLangCode() {
        return this.langCode;
    }
}

