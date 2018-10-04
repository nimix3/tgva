
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTMessagesStateInfo
extends TLObject {
    public static final int CLASS_ID = 81704317;
    private Long req_msg_id;
    private String info;

    public MTMessagesStateInfo() {
    }

    public MTMessagesStateInfo(Long reqMsgId, String info) {
        this.req_msg_id = reqMsgId;
        this.info = info;
    }

    @Override
    public int getClassId() {
        return 81704317;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.req_msg_id, stream);
        StreamingUtils.writeTLString(this.info, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.req_msg_id = StreamingUtils.readLong(stream);
        this.info = StreamingUtils.readTLString(stream);
    }

    public Long getReqMsgId() {
        return this.req_msg_id;
    }

    public void setReqMsgId(Long req_msg_id) {
        this.req_msg_id = req_msg_id;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "mtMessagesStateInfo#04deb57d";
    }
}

