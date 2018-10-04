
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.peer.TLPeerSettings;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetPeerSettings
extends TLMethod<TLPeerSettings> {
    public static final int CLASS_ID = 913498268;
    private TLAbsInputUser peer;

    @Override
    public int getClassId() {
        return 913498268;
    }

    @Override
    public TLPeerSettings deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLPeerSettings) {
            return (TLPeerSettings)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLPeerSettings.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputUser getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputUser peer) {
        this.peer = peer;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.getPeerSettings#3672e09c";
    }
}

