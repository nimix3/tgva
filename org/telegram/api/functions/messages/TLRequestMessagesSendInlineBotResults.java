
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSendInlineBotResults
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -1318189314;
    private static final int FLAG_REPLY = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_UNUSED2 = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_BROADCAST = 16;
    private static final int FLAG_SILENT = 32;
    private static final int FLAG_BACKGROUND = 64;
    private int flags;
    private TLAbsInputPeer peer;
    private int replyToMsgId;
    private long randomId;
    private long queryId;
    private String id;

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer peer) {
        this.peer = peer;
    }

    public int getReplyToMsgId() {
        return this.replyToMsgId;
    }

    public void setReplyToMsgId(int replyToMsgId) {
        this.replyToMsgId = replyToMsgId;
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long randomId) {
        this.randomId = randomId;
    }

    public long getQueryId() {
        return this.queryId;
    }

    public void setQueryId(long queryId) {
        this.queryId = queryId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getClassId() {
        return -1318189314;
    }

    @Override
    public TLAbsUpdates deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdates) {
            return (TLAbsUpdates)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.replyToMsgId, stream);
        }
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeLong(this.queryId, stream);
        StreamingUtils.writeTLString(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 1) != 0) {
            this.replyToMsgId = StreamingUtils.readInt(stream);
        }
        this.randomId = StreamingUtils.readLong(stream);
        this.queryId = StreamingUtils.readLong(stream);
        this.id = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messages.sendInlineBotResult#b16e06fe";
    }
}

