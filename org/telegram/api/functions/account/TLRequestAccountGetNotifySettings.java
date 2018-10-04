
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.notify.TLAbsInputNotifyPeer;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetNotifySettings
extends TLMethod<TLAbsPeerNotifySettings> {
    public static final int CLASS_ID = 313765169;
    private TLAbsInputNotifyPeer peer;

    @Override
    public int getClassId() {
        return 313765169;
    }

    @Override
    public TLAbsPeerNotifySettings deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsPeerNotifySettings) {
            return (TLAbsPeerNotifySettings)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputNotifyPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputNotifyPeer value) {
        this.peer = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputNotifyPeer)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.getNotifySettings#12b3ad31";
    }
}

