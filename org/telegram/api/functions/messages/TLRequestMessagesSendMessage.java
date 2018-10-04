
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesSendMessage
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -91733382;
    private static final int FLAG_REPLY = 1;
    private static final int FLAG_NOWEBPREVIEW = 2;
    private static final int FLAG_REPLYMARKUP = 4;
    private static final int FLAG_ENTITIES = 8;
    private static final int FLAG_BROADCAST = 16;
    private static final int FLAG_SILENT = 32;
    private static final int FLAG_BACKGROUND = 64;
    private int flags;
    private TLAbsInputPeer peer;
    private int replyToMsgId;
    private String message;
    private long randomId;
    private TLAbsReplyMarkup replyMarkup;
    private TLVector<TLAbsMessageEntity> entities;

    @Override
    public int getClassId() {
        return -91733382;
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

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public void setPeer(TLAbsInputPeer value) {
        this.peer = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
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
        this.flags |= 1;
        this.replyToMsgId = replyToMsgId;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    public void setReplyMarkup(TLAbsReplyMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }

    public TLVector<TLAbsMessageEntity> getEntities() {
        return this.entities;
    }

    public void setEntities(TLVector<TLAbsMessageEntity> entities) {
        this.entities = entities;
        this.flags |= 8;
    }

    public void disableWebPreview() {
        this.flags |= 2;
    }

    public void enableBroadcast(boolean enabled) {
        this.flags = enabled ? (this.flags |= 16) : (this.flags &= -17);
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.replyToMsgId, stream);
        }
        StreamingUtils.writeTLString(this.message, stream);
        StreamingUtils.writeLong(this.randomId, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLVector(this.entities, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 1) != 0) {
            this.replyToMsgId = StreamingUtils.readInt(stream);
        }
        this.message = StreamingUtils.readTLString(stream);
        this.randomId = StreamingUtils.readLong(stream);
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 8) != 0) {
            this.entities = StreamingUtils.readTLVector(stream, context);
        }
    }

    @Override
    public String toString() {
        return "messages.sendMessage#fa88427a";
    }
}

