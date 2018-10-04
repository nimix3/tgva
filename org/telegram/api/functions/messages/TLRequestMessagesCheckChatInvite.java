
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesCheckChatInvite
extends TLMethod<TLAbsChatInvite> {
    public static final int CLASS_ID = 1051570619;
    private String hash;

    @Override
    public int getClassId() {
        return 1051570619;
    }

    @Override
    public TLAbsChatInvite deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsChatInvite) {
            return (TLAbsChatInvite)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.chat.TLAbsChatInvite, got: " + res.getClass().getCanonicalName());
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "request.checkChatInvite#3eadb1bb";
    }
}

