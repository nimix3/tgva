
package org.telegram.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.TLDcOption;
import org.telegram.api.disablefeature.TLDisabledFeature;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLConfig
extends TLObject {
    public static final int CLASS_ID = 830271220;
    private int date;
    private int expires;
    private boolean testMode;
    private int thisDc;
    private TLVector<TLDcOption> dcOptions;
    private int chatSizeMax;
    private int megaGroupSizeMax;
    private int forwardedCountMax;
    private int onlineUpdatePeriodMs;
    private int offlineBlurTimeoutMs;
    private int offlineIdleTimeoutMs;
    private int onlineCloudTimeoutMs;
    private int notifyCloudDelayMs;
    private int notifyDefaultDelayMs;
    private int chatBigSize;
    private int pushChatPeriodMs;
    private int pushChatLimit;
    private int savedGifsLimits;
    private int editTimeLimit;
    private TLVector<TLDisabledFeature> disabledFeatures = new TLVector();

    @Override
    public int getClassId() {
        return 830271220;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    public boolean getTestMode() {
        return this.testMode;
    }

    public int getThisDc() {
        return this.thisDc;
    }

    public void setThisDc(int value) {
        this.thisDc = value;
    }

    public TLVector<TLDcOption> getDcOptions() {
        return this.dcOptions;
    }

    public void setDcOptions(TLVector<TLDcOption> value) {
        this.dcOptions = value;
    }

    public int getChatSizeMax() {
        return this.chatSizeMax;
    }

    public void setChatSizeMax(int value) {
        this.chatSizeMax = value;
    }

    public boolean isTestMode() {
        return this.testMode;
    }

    public void setTestMode(boolean value) {
        this.testMode = value;
    }

    public int getExpires() {
        return this.expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public int getForwardedCountMax() {
        return this.forwardedCountMax;
    }

    public void setForwardedCountMax(int forwardedCountMax) {
        this.forwardedCountMax = forwardedCountMax;
    }

    public int getOnlineUpdatePeriodMs() {
        return this.onlineUpdatePeriodMs;
    }

    public void setOnlineUpdatePeriodMs(int onlineUpdatePeriodMs) {
        this.onlineUpdatePeriodMs = onlineUpdatePeriodMs;
    }

    public int getOfflineBlurTimeoutMs() {
        return this.offlineBlurTimeoutMs;
    }

    public void setOfflineBlurTimeoutMs(int offlineBlurTimeoutMs) {
        this.offlineBlurTimeoutMs = offlineBlurTimeoutMs;
    }

    public int getOfflineIdleTimeoutMs() {
        return this.offlineIdleTimeoutMs;
    }

    public void setOfflineIdleTimeoutMs(int offlineIdleTimeoutMs) {
        this.offlineIdleTimeoutMs = offlineIdleTimeoutMs;
    }

    public int getOnlineCloudTimeoutMs() {
        return this.onlineCloudTimeoutMs;
    }

    public void setOnlineCloudTimeoutMs(int onlineCloudTimeoutMs) {
        this.onlineCloudTimeoutMs = onlineCloudTimeoutMs;
    }

    public int getNotifyCloudDelayMs() {
        return this.notifyCloudDelayMs;
    }

    public void setNotifyCloudDelayMs(int notifyCloudDelayMs) {
        this.notifyCloudDelayMs = notifyCloudDelayMs;
    }

    public int getNotifyDefaultDelayMs() {
        return this.notifyDefaultDelayMs;
    }

    public void setNotifyDefaultDelayMs(int notifyDefaultDelayMs) {
        this.notifyDefaultDelayMs = notifyDefaultDelayMs;
    }

    public int getChatBigSize() {
        return this.chatBigSize;
    }

    public void setChatBigSize(int chatBigSize) {
        this.chatBigSize = chatBigSize;
    }

    public int getPushChatPeriodMs() {
        return this.pushChatPeriodMs;
    }

    public void setPushChatPeriodMs(int pushChatPeriodMs) {
        this.pushChatPeriodMs = pushChatPeriodMs;
    }

    public int getPushChatLimit() {
        return this.pushChatLimit;
    }

    public void setPushChatLimit(int pushChatLimit) {
        this.pushChatLimit = pushChatLimit;
    }

    public int getMegaGroupSizeMax() {
        return this.megaGroupSizeMax;
    }

    public void setMegaGroupSizeMax(int megaGroupSizeMax) {
        this.megaGroupSizeMax = megaGroupSizeMax;
    }

    public int getSavedGifsLimits() {
        return this.savedGifsLimits;
    }

    public void setSavedGifsLimits(int savedGifsLimits) {
        this.savedGifsLimits = savedGifsLimits;
    }

    public int getEditTimeLimit() {
        return this.editTimeLimit;
    }

    public void setEditTimeLimit(int editTimeLimit) {
        this.editTimeLimit = editTimeLimit;
    }

    public TLVector<TLDisabledFeature> getDisabledFeatures() {
        return this.disabledFeatures;
    }

    public void setDisabledFeatures(TLVector<TLDisabledFeature> disabledFeatures) {
        this.disabledFeatures = disabledFeatures;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.expires, stream);
        StreamingUtils.writeTLBool(this.testMode, stream);
        StreamingUtils.writeInt(this.thisDc, stream);
        StreamingUtils.writeTLVector(this.dcOptions, stream);
        StreamingUtils.writeInt(this.chatSizeMax, stream);
        StreamingUtils.writeInt(this.megaGroupSizeMax, stream);
        StreamingUtils.writeInt(this.forwardedCountMax, stream);
        StreamingUtils.writeInt(this.onlineUpdatePeriodMs, stream);
        StreamingUtils.writeInt(this.offlineBlurTimeoutMs, stream);
        StreamingUtils.writeInt(this.offlineIdleTimeoutMs, stream);
        StreamingUtils.writeInt(this.onlineCloudTimeoutMs, stream);
        StreamingUtils.writeInt(this.notifyCloudDelayMs, stream);
        StreamingUtils.writeInt(this.notifyDefaultDelayMs, stream);
        StreamingUtils.writeInt(this.chatBigSize, stream);
        StreamingUtils.writeInt(this.pushChatPeriodMs, stream);
        StreamingUtils.writeInt(this.pushChatLimit, stream);
        StreamingUtils.writeInt(this.savedGifsLimits, stream);
        StreamingUtils.writeInt(this.editTimeLimit, stream);
        StreamingUtils.writeTLVector(this.disabledFeatures, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.date = StreamingUtils.readInt(stream);
        this.expires = StreamingUtils.readInt(stream);
        this.testMode = StreamingUtils.readTLBool(stream);
        this.thisDc = StreamingUtils.readInt(stream);
        this.dcOptions = StreamingUtils.readTLVector(stream, context);
        this.chatSizeMax = StreamingUtils.readInt(stream);
        this.megaGroupSizeMax = StreamingUtils.readInt(stream);
        this.forwardedCountMax = StreamingUtils.readInt(stream);
        this.onlineUpdatePeriodMs = StreamingUtils.readInt(stream);
        this.offlineBlurTimeoutMs = StreamingUtils.readInt(stream);
        this.offlineIdleTimeoutMs = StreamingUtils.readInt(stream);
        this.onlineCloudTimeoutMs = StreamingUtils.readInt(stream);
        this.notifyCloudDelayMs = StreamingUtils.readInt(stream);
        this.notifyDefaultDelayMs = StreamingUtils.readInt(stream);
        this.chatBigSize = StreamingUtils.readInt(stream);
        this.pushChatPeriodMs = StreamingUtils.readInt(stream);
        this.pushChatLimit = StreamingUtils.readInt(stream);
        this.savedGifsLimits = StreamingUtils.readInt(stream);
        this.editTimeLimit = StreamingUtils.readInt(stream);
        this.disabledFeatures = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "config#317ceef4";
    }
}

