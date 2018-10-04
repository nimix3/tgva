
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLUpdateBotCallbackQuery
extends TLAbsUpdate {
    public static final int CLASS_ID = -1500747636;
    private long queryId;
    private int userId;
    private TLAbsPeer peer;
    private int msgId;
    private TLBytes data;

    @Override
    public int getClassId() {
        return -1500747636;
    }

    public long getQueryId() {
        return this.queryId;
    }

    public int getUserId() {
        return this.userId;
    }

    public TLAbsPeer getPeer() {
        return this.peer;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public TLBytes getData() {
        return this.data;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.queryId, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.msgId, stream);
        StreamingUtils.writeTLBytes(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.queryId = StreamingUtils.readLong(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.peer = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        this.msgId = StreamingUtils.readInt(stream);
        this.data = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "updateBotCallbackQuery#a68c688c";
    }
}

