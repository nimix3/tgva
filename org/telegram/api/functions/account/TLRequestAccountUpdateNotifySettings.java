
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.notify.TLAbsInputNotifyPeer;
import org.telegram.api.input.peer.notify.TLInputPeerNotifySettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountUpdateNotifySettings
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -2067899501;
    private TLAbsInputNotifyPeer peer;
    private TLInputPeerNotifySettings settings;

    @Override
    public int getClassId() {
        return -2067899501;
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

    public TLAbsInputNotifyPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputNotifyPeer value) {
        this.peer = value;
    }

    public TLInputPeerNotifySettings getSettings() {
        return this.settings;
    }

    public void setSettings(TLInputPeerNotifySettings value) {
        this.settings = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLObject(this.settings, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputNotifyPeer)StreamingUtils.readTLObject(stream, context);
        this.settings = (TLInputPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.updateNotifySettings#84be5b93";
    }
}

