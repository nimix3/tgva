
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;

public class MTMessagesAllInfo
extends TLObject {
    public static final int CLASS_ID = -1933520591;
    private TLLongVector msgIds;
    private String info;

    public MTMessagesAllInfo() {
    }

    public MTMessagesAllInfo(TLLongVector msgIds, String info) {
        this.msgIds = msgIds;
        this.info = info;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.msgIds, stream);
        StreamingUtils.writeTLString(this.info, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.msgIds = StreamingUtils.readTLLongVector(stream, context);
        this.info = StreamingUtils.readTLString(stream);
    }

    public TLLongVector getMsgIds() {
        return this.msgIds;
    }

    public void setMsgIds(TLLongVector msgIds) {
        this.msgIds = msgIds;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int getClassId() {
        return -1933520591;
    }

    @Override
    public String toString() {
        return "mtMessagesAllInfo#8cc0d131";
    }
}

