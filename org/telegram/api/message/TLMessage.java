
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.TLMessageFwdHeader;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.api.message.media.TLMessageMediaEmpty;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLMessage
extends TLAbsMessage {
    public static final int CLASS_ID = -1063525281;
    private static final int FLAG_UNREAD = 1;
    private static final int FLAG_OUT = 2;
    private static final int FLAG_FWD = 4;
    private static final int FLAG_REPLY = 8;
    private static final int FLAG_MENTION = 16;
    private static final int FLAG_MEDIA_UNREAD = 32;
    private static final int FLAG_REPLYKEYBOARD = 64;
    private static final int FLAG_ENTITIES = 128;
    private static final int FLAG_FROMID = 256;
    private static final int FLAG_MEDIA = 512;
    private static final int FLAG_VIEWS = 1024;
    private static final int FLAG_VIA_BOT_ID = 2048;
    private static final int FLAG_UNUSED_12 = 4096;
    private static final int FLAG_SILENT = 8192;
    private static final int FLAG_POST = 16384;
    private static final int FLAG_EDIT_DATE = 32768;
    private int flags;
    private int id;
    private int fromId;
    private TLAbsPeer toId;
    private TLMessageFwdHeader fwdFrom;
    private int viaBotId;
    private int replyToMsgId;
    private int date;
    private String message;
    private TLAbsMessageMedia media;
    private TLAbsReplyMarkup replyMarkup;
    private TLVector<TLAbsMessageEntity> entities;
    private int views;
    private int editDate;

    @Override
    public int getClassId() {
        return -1063525281;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TLAbsMessageMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsMessageMedia media) {
        this.media = media;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public TLAbsPeer getToId() {
        return this.toId;
    }

    public void setToId(TLAbsPeer toId) {
        this.toId = toId;
    }

    @Override
    public int getChatId() {
        return this.getToId().getId();
    }

    public int getFromId() {
        return this.fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getEditDate() {
        return this.editDate;
    }

    public void setEditDate(int editDate) {
        this.editDate = editDate;
    }

    public int getReplyToMsgId() {
        return this.replyToMsgId;
    }

    public void setReplyToMsgId(int replyToMsgId) {
        this.replyToMsgId = replyToMsgId;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    public void setReplyMarkup(TLAbsReplyMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }

    public TLVector<TLAbsMessageEntity> getEntities() {
        return this.hasEntities() ? this.entities : new TLVector<TLAbsMessageEntity>();
    }

    public void setEntities(TLVector<TLAbsMessageEntity> entities) {
        this.entities = entities;
        this.toggleEntities(entities != null && !entities.isEmpty());
    }

    private void toggleEntities(boolean enabled) {
        this.flags = enabled ? (this.flags |= 128) : (this.flags &= -129);
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
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

    public boolean isForwarded() {
        return (this.flags & 4) != 0;
    }

    public boolean isReply() {
        return (this.flags & 8) != 0;
    }

    public boolean hasReplyKeyboard() {
        return (this.flags & 64) != 0;
    }

    public boolean hasEntities() {
        return (this.flags & 128) != 0;
    }

    public boolean hasText() {
        return this.message == null || !this.message.isEmpty();
    }

    public boolean hasMedia() {
        return (this.flags & 512) != 0 && !(this.media instanceof TLMessageMediaEmpty);
    }

    public int getViaBotId() {
        return this.viaBotId;
    }

    public void setViaBotId(int viaBotId) {
        this.viaBotId = viaBotId;
    }

    public TLMessageFwdHeader getFwdFrom() {
        return this.fwdFrom;
    }

    public void setFwdFrom(TLMessageFwdHeader fwdFrom) {
        this.fwdFrom = fwdFrom;
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
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.fwdFrom, stream);
        }
        if ((this.flags & 2048) != 0) {
            StreamingUtils.writeInt(this.viaBotId, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeInt(this.replyToMsgId, stream);
        }
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLString(this.message, stream);
        if ((this.flags & 512) != 0) {
            StreamingUtils.writeTLObject(this.media, stream);
        }
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
        if ((this.flags & 128) != 0) {
            StreamingUtils.writeTLVector(this.entities, stream);
        }
        if ((this.flags & 1024) != 0) {
            StreamingUtils.writeInt(this.views, stream);
        }
        if ((this.flags & 32768) != 0) {
            StreamingUtils.writeInt(this.editDate, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        if ((this.flags & 256) != 0) {
            this.fromId = StreamingUtils.readInt(stream);
        }
        this.toId = (TLAbsPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 4) != 0) {
            this.fwdFrom = (TLMessageFwdHeader)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 2048) != 0) {
            this.viaBotId = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 8) != 0) {
            this.replyToMsgId = StreamingUtils.readInt(stream);
        }
        this.date = StreamingUtils.readInt(stream);
        this.message = StreamingUtils.readTLString(stream);
        if ((this.flags & 512) != 0) {
            this.media = (TLAbsMessageMedia)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 64) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 128) != 0) {
            this.entities = StreamingUtils.readTLVector(stream, context);
        }
        if ((this.flags & 1024) != 0) {
            this.views = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 32768) != 0) {
            this.editDate = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "message#c09be45f";
    }
}

