
package org.telegram.api.functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestInitConnection
extends TLMethod<TLObject> {
    public static final int CLASS_ID = 1769565673;
    private int apiId;
    private String deviceModel;
    private String systemVersion;
    private String appVersion;
    private String langCode;
    private TLMethod query;

    public TLRequestInitConnection() {
    }

    public TLRequestInitConnection(int apiId, String deviceModel, String systemVersion, String appVersion, String langCode, TLMethod query) {
        this.apiId = apiId;
        this.deviceModel = deviceModel;
        this.systemVersion = systemVersion;
        this.appVersion = appVersion;
        this.langCode = langCode;
        this.query = query;
    }

    @Override
    public int getClassId() {
        return 1769565673;
    }

    @Override
    public TLObject deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return this.query.deserializeResponse(stream, context);
    }

    public int getApiId() {
        return this.apiId;
    }

    public void setApiId(int value) {
        this.apiId = value;
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

    public TLMethod getQuery() {
        return this.query;
    }

    public void setQuery(TLMethod value) {
        this.query = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.apiId, stream);
        StreamingUtils.writeTLString(this.deviceModel, stream);
        StreamingUtils.writeTLString(this.systemVersion, stream);
        StreamingUtils.writeTLString(this.appVersion, stream);
        StreamingUtils.writeTLString(this.langCode, stream);
        StreamingUtils.writeTLMethod(this.query, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.apiId = StreamingUtils.readInt(stream);
        this.deviceModel = StreamingUtils.readTLString(stream);
        this.systemVersion = StreamingUtils.readTLString(stream);
        this.appVersion = StreamingUtils.readTLString(stream);
        this.langCode = StreamingUtils.readTLString(stream);
        this.query = StreamingUtils.readTLMethod(stream, context);
    }

    @Override
    public String toString() {
        return "initConnection#69796de9";
    }
}

