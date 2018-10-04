
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesStartBot
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -421563528;
    private TLAbsInputUser bot;
    private TLAbsInputPeer peer;
    private long randomId;
    private String startParam;

    @Override
    public int getClassId() {
        return -421563528;
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
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputUser getBot() {
        return this.bot;
    }

    public void setBot(TLAbsInputUser bot) {
        this.bot = bot;
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer peer) {
        this.peer = peer;
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long randomId) {
        this.randomId = randomId;
    }

    public String getStartParam() {
        return this.startParam;
    }

    public void setStartParam(String startParam) {
        this.startParam = startParam;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.bot, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeTLString(this.startParam, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.bot = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.randomId = StreamingUtils.readLong(stream);
        this.startParam = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "bot.startBot#e6df7378";
    }
}

