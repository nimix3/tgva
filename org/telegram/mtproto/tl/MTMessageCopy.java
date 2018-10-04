
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.MTMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTMessageCopy
extends TLObject {
    public static final int CLASS_ID = -530561358;
    private MTMessage orig_message;

    public MTMessageCopy() {
    }

    public MTMessageCopy(MTMessage orig_message) {
        this.orig_message = orig_message;
    }

    public MTMessage getOrig_message() {
        return this.orig_message;
    }

    public void setOrig_message(MTMessage orig_message) {
        this.orig_message = orig_message;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.orig_message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.orig_message = (MTMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public int getClassId() {
        return -530561358;
    }

    @Override
    public String toString() {
        return "MTMessageCopy#e06046b2";
    }
}

