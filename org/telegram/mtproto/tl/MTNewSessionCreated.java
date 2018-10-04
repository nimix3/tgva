
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTNewSessionCreated
extends TLObject {
    public static final int CLASS_ID = -1631450872;
    private long firstMsgId;
    private long uniqId;
    private long serverSalt;

    public MTNewSessionCreated(long firstMsgId, long uniqId, long serverSalt) {
        this.firstMsgId = firstMsgId;
        this.uniqId = uniqId;
        this.serverSalt = serverSalt;
    }

    public MTNewSessionCreated() {
    }

    public long getFirstMsgId() {
        return this.firstMsgId;
    }

    public long getUniqId() {
        return this.uniqId;
    }

    public long getServerSalt() {
        return this.serverSalt;
    }

    @Override
    public int getClassId() {
        return -1631450872;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.firstMsgId, stream);
        StreamingUtils.writeLong(this.uniqId, stream);
        StreamingUtils.writeLong(this.serverSalt, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.firstMsgId = StreamingUtils.readLong(stream);
        this.uniqId = StreamingUtils.readLong(stream);
        this.serverSalt = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "new_session_created#9ec20908";
    }
}

