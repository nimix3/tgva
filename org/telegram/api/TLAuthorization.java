
package org.telegram.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAuthorization
extends TLObject {
    public static final int CLASS_ID = 2079516406;
    private long hash;
    private int flags;
    private String deviceModel;
    private String platform;
    private String systemVersion;
    private int apiId;
    private String appName;
    private String appVersion;
    private int dateCreated;
    private int dateActive;
    private String ip;
    private String country;
    private String region;

    @Override
    public int getClassId() {
        return 2079516406;
    }

    public long getHash() {
        return this.hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public int getApiId() {
        return this.apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(int dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDateActive() {
        return this.dateActive;
    }

    public void setDateActive(int dateActive) {
        this.dateActive = dateActive;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.hash, stream);
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.deviceModel, stream);
        StreamingUtils.writeTLString(this.platform, stream);
        StreamingUtils.writeTLString(this.systemVersion, stream);
        StreamingUtils.writeInt(this.apiId, stream);
        StreamingUtils.writeTLString(this.appName, stream);
        StreamingUtils.writeTLString(this.appVersion, stream);
        StreamingUtils.writeInt(this.dateCreated, stream);
        StreamingUtils.writeInt(this.dateActive, stream);
        StreamingUtils.writeTLString(this.ip, stream);
        StreamingUtils.writeTLString(this.country, stream);
        StreamingUtils.writeTLString(this.region, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.hash = StreamingUtils.readLong(stream);
        this.flags = StreamingUtils.readInt(stream);
        this.deviceModel = StreamingUtils.readTLString(stream);
        this.platform = StreamingUtils.readTLString(stream);
        this.systemVersion = StreamingUtils.readTLString(stream);
        this.apiId = StreamingUtils.readInt(stream);
        this.appName = StreamingUtils.readTLString(stream);
        this.appVersion = StreamingUtils.readTLString(stream);
        this.dateCreated = StreamingUtils.readInt(stream);
        this.dateActive = StreamingUtils.readInt(stream);
        this.ip = StreamingUtils.readTLString(stream);
        this.country = StreamingUtils.readTLString(stream);
        this.region = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "authorization#7bf2e6f6";
    }
}

