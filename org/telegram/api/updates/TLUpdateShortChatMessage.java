
package org.telegram.api.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLMessageFwdHeader;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdateShortChatMessage
extends TLAbsUpdates {
    public static final int CLASS_ID = 377562760;
    private static final int FLAG_UNREAD = 1;
    private static final int FLAG_OUT = 2;
    private static final int FLAG_FWD = 4;
    private static final int FLAG_REPLY = 8;
    private static final int FLAG_MENTION = 16;
    private static final int FLAG_CONTENT_UNREAD = 32;
    private static final int FLAG_UNUSED6 = 64;
    private static final int FLAG_ENTITIES = 128;
    private static final int FLAG_UNUSED8 = 256;
    private static final int FLAG_UNUSED9 = 512;
    private static final int FLAG_UNUSED10 = 1024;
    private static final int FLAG_VIA_BOT_ID = 2048;
    private static final int FLAG_UNUSED_12 = 4096;
    private static final int FLAG_SILENT = 8192;
    private int flags;
    private int id;
    private int fromId;
    private int chatId;
    private String message = "";
    private int pts;
    private int ptsCount;
    private int date;
    private TLMessageFwdHeader fwdFrom;
    private int viaBotId;
    private int replyToMsgId;
    private TLVector<TLAbsMessageEntity> entities;

    public int getFromId() {
        return this.fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLMessageFwdHeader getFwdFrom() {
        return this.fwdFrom;
    }

    public void setFwdFrom(TLMessageFwdHeader fwdFrom) {
        this.fwdFrom = fwdFrom;
    }

    public int getReplyToMsgId() {
        return this.replyToMsgId;
    }

    public void setReplyToMsgId(int replyToMsgId) {
        this.replyToMsgId = replyToMsgId;
    }

    public TLVector<TLAbsMessageEntity> getEntities() {
        return this.hasEntities() ? this.entities : new TLVector<TLAbsMessageEntity>();
    }

    public void setEntities(TLVector<TLAbsMessageEntity> entities) {
        this.entities = entities;
    }

    @Override
    public int getClassId() {
        return 377562760;
    }

    public boolean isMention() {
        return (this.flags & 16) != 0;
    }

    public boolean isSent() {
        return (this.flags & 2) != 0;
    }

    public boolean isUnread() {
        return (this.flags & 1) != 0;
    }

    public boolean isUnreadContent() {
        return (this.flags & 32) != 0;
    }

    public boolean isForwarded() {
        return (this.flags & 4) != 0;
    }

    public boolean hasEntities() {
        return (this.flags & 128) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeInt(this.fromId, stream);
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLString(this.message, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
        StreamingUtils.writeInt(this.date, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.fwdFrom, stream);
        }
        if ((this.flags & 2048) != 0) {
            StreamingUtils.writeInt(this.viaBotId, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeInt(this.replyToMsgId, stream);
        }
        if ((this.flags & 128) != 0) {
            StreamingUtils.writeTLVector(this.entities, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.fromId = StreamingUtils.readInt(stream);
        this.chatId = StreamingUtils.readInt(stream);
        this.message = StreamingUtils.readTLString(stream);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        if ((this.flags & 4) != 0) {
            this.fwdFrom = (TLMessageFwdHeader)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 2048) != 0) {
            this.viaBotId = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 8) != 0) {
            this.replyToMsgId = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 128) != 0) {
            this.entities = StreamingUtils.readTLVector(stream, context);
        }
    }

    @Override
    public String toString() {
        return "updateShortChatMessage#16812688";
    }
}

