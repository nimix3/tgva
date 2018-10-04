
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.sendmessage.action.TLAbsSendMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSetTyping
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -1551737264;
    private TLAbsInputPeer peer;
    private TLAbsSendMessageAction action;

    @Override
    public int getClassId() {
        return -1551737264;
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

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    public TLAbsSendMessageAction getAction() {
        return this.action;
    }

    public void setAction(TLAbsSendMessageAction action) {
        this.action = action;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.action = (TLAbsSendMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.setTyping#a3825e50";
    }
}

