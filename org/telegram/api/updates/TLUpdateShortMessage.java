
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

public class TLUpdateShortMessage
extends TLAbsUpdates {
    public static final int CLASS_ID = -1857044719;
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
    private int userId;
    private String message = "";
    private int pts;
    private int ptsCount;
    private int date;
    private TLMessageFwdHeader fwdFrom;
    private int viaBotId;
    private int replyToMsgId;
    private TLVector<TLAbsMessageEntity> entities;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
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

    public int getViaBotId() {
        return this.viaBotId;
    }

    public void setViaBotId(int viaBotId) {
        this.viaBotId = viaBotId;
    }

    public boolean hasText() {
        return this.message == null || !this.message.isEmpty();
    }

    public boolean isForwarded() {
        return (this.flags & 4) != 0;
    }

    public boolean isReply() {
        return (this.flags & 8) != 0;
    }

    public boolean isSent() {
        return (this.flags & 2) != 0;
    }

    public boolean isUnread() {
        return (this.flags & 1) != 0;
    }

    public boolean isMention() {
        return (this.flags & 16) != 0;
    }

    public boolean isUnreadContent() {
        return (this.flags & 32) != 0;
    }

    public boolean hasEntities() {
        return (this.flags & 128) != 0;
    }

    @Override
    public int getClassId() {
        return -1857044719;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeInt(this.userId, stream);
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
        this.userId = StreamingUtils.readInt(stream);
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
        return "updateShortMessage#914fbf11";
    }
}

