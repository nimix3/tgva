
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTInvokeAfter
extends TLObject {
    public static final int CLASS_ID = -878758099;
    private long dependMsgId;
    private byte[] request;

    public MTInvokeAfter(long dependMsgId, byte[] request) {
        this.dependMsgId = dependMsgId;
        this.request = request;
    }

    public long getDependMsgId() {
        return this.dependMsgId;
    }

    public byte[] getRequest() {
        return this.request;
    }

    @Override
    public int getClassId() {
        return -878758099;
    }

    @Override
    public String toString() {
        return "mTInvokeAfter#cb9f372d";
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.dependMsgId, stream);
        StreamingUtils.writeByteArray(this.request, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        throw new UnsupportedOperationException("Unable to deserialize invokeAfterMsg#-878758099");
    }
}

