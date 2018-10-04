
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;

public class MTMsgsStateReq
extends TLObject {
    public static final int CLASS_ID = -630588590;
    private TLLongVector messages;

    public MTMsgsStateReq(TLLongVector messages) {
        this.messages = messages;
    }

    public MTMsgsStateReq() {
        this.messages = new TLLongVector();
    }

    public MTMsgsStateReq(long[] msgIds) {
        this.messages = new TLLongVector();
        for (long id : msgIds) {
            this.messages.add(id);
        }
    }

    public MTMsgsStateReq(Long[] msgIds) {
        this.messages = new TLLongVector();
        Collections.addAll(this.messages, msgIds);
    }

    public TLLongVector getMessages() {
        return this.messages;
    }

    @Override
    public int getClassId() {
        return -630588590;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.messages, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.messages = StreamingUtils.readTLLongVector(stream, context);
    }

    @Override
    public String toString() {
        return "msg_resend_req#da69fb52";
    }
}

