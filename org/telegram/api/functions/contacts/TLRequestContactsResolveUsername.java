
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.TLResolvedPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsResolveUsername
extends TLMethod<TLResolvedPeer> {
    public static final int CLASS_ID = -113456221;
    private String username;

    @Override
    public int getClassId() {
        return -113456221;
    }

    @Override
    public TLResolvedPeer deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLResolvedPeer) {
            return (TLResolvedPeer)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLResolvedPeer.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.username, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.username = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "contacts.resolveUsername#f93ccba3";
    }
}

