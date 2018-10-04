
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSendMedia
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -923703407;
    private static final int FLAG_REPLY = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_REPLYMARKUP = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_BROADCAST = 16;
    private static final int FLAG_SILENT = 32;
    private static final int FLAG_BACKGROUND = 64;
    private int flags;
    private TLAbsInputPeer peer;
    private TLAbsInputMedia media;
    private long randomId;
    private int replyToMsgId;
    private TLAbsReplyMarkup replyMarkup;

    @Override
    public int getClassId() {
        return -923703407;
    }

    public void computeFlags(boolean broadcast, boolean silent, boolean background) {
        this.flags = 0;
        this.flags = broadcast ? this.flags | 16 : this.flags & -17;
        this.flags = silent ? this.flags | 32 : this.flags & -33;
        this.flags = background ? this.flags | 64 : this.flags & -65;
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
        throw new IOException("Incorrect response type. Expected org.telegram.api.updates.TLAbsUpdates, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    public TLAbsInputMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsInputMedia value) {
        this.media = value;
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long value) {
        this.randomId = value;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getReplyToMsgId() {
        return this.replyToMsgId;
    }

    public void setReplyToMsgId(int replyToMsgId) {
        this.replyToMsgId = replyToMsgId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.replyToMsgId, stream);
        }
        StreamingUtils.writeTLObject(this.media, stream);
        StreamingUtils.writeLong(this.randomId, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 1) != 0) {
            this.replyToMsgId = StreamingUtils.readInt(stream);
        }
        this.media = (TLAbsInputMedia)StreamingUtils.readTLObject(stream, context);
        this.randomId = StreamingUtils.readLong(stream);
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "messages.sendMedia#c8f16791";
    }
}

