
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.action.TLAbsMessageAction;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageService
extends TLAbsMessage {
    public static final int CLASS_ID = -1642487306;
    private static final int FLAG_UNREAD = 1;
    private static final int FLAG_OUT = 2;
    private static final int FLAG_UNUSED2 = 4;
    private static final int FLAG_REPLY_TO_MSG_ID = 8;
    private static final int FLAG_MENTIONED = 16;
    private static final int FLAG_MEDIA_UNREAD = 32;
    private static final int FLAG_UNUSED7 = 64;
    private static final int FLAG_UNUSED8 = 128;
    private static final int FLAG_FROMID = 256;
    private static final int FLAG_UNUSED_9 = 512;
    private static final int FLAG_UNUSED_10 = 1024;
    private static final int FLAG_UNUSED_11 = 2048;
    private static final int FLAG_UNUSED_12 = 4096;
    private static final int FLAG_SILENT = 8192;
    private static final int FLAG_POST = 16384;
    private int flags;
    private int id;
    private int fromId;
    private TLAbsPeer toId;
    private int replyToMessageId;
    private int date;
    private TLAbsMessageAction action;

    @Override
    public int getClassId() {
        return -1642487306;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLAbsMessageAction getAction() {
        return this.action;
    }

    public void setAction(TLAbsMessageAction action) {
        this.action = action;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public TLAbsPeer getToId() {
        return this.toId;
    }

    @Override
    public int getChatId() {
        return this.getToId().getId();
    }

    public void setToId(TLAbsPeer toId) {
        this.toId = toId;
    }

    public int getFromId() {
        return this.fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReplyToMessageId() {
        return this.replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public boolean hasFromId() {
        return (this.flags & 256) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        if ((this.flags & 256) != 0) {
            StreamingUtils.writeInt(this.fromId, stream);
        }
        StreamingUtils.writeTLObject(this.toId, stream);
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeInt(this.replyToMessageId, stream);
        }
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLObject(this.action, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        if ((this.flags & 256) != 0) {
            this.fromId = StreamingUtils.readInt(stream);
        }
        this.toId = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 8) != 0) {
            this.replyToMessageId = StreamingUtils.readInt(stream);
        }
        this.date = StreamingUtils.readInt(stream);
        this.action = (TLAbsMessageAction)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messageService#9e19a1f6";
    }
}

