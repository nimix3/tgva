
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTRpcDropAnswer
extends TLObject {
    public static final int CLASS_ID = 1491380032;
    private Long req_msg_id;

    public MTRpcDropAnswer(Long req_msg_id) {
        this.req_msg_id = req_msg_id;
    }

    public MTRpcDropAnswer() {
    }

    public Long getReq_msg_id() {
        return this.req_msg_id;
    }

    public void setReq_msg_id(Long req_msg_id) {
        this.req_msg_id = req_msg_id;
    }

    @Override
    public int getClassId() {
        return 1491380032;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.req_msg_id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.req_msg_id = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "msg_resend_req#58e4a740";
    }
}

