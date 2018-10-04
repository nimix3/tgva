
package org.telegram.api.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdateShortSentMessage
extends TLAbsUpdates {
    public static final int CLASS_ID = 301019932;
    private static final int FLAG_UNREAD = 1;
    private static final int FLAG_OUT = 2;
    private static final int FLAG_UNUSED2 = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_UNUSED4 = 16;
    private static final int FLAG_UNUSED5 = 32;
    private static final int FLAG_UNUSED6 = 64;
    private static final int FLAG_ENTITIES = 128;
    private static final int FLAG_UNUSED8 = 256;
    private static final int FLAG_MEDIA = 512;
    private int flags;
    private int id;
    private int pts;
    private int ptsCount;
    private int date;
    private TLAbsMessageMedia media;
    private TLVector<TLAbsMessageEntity> entities;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TLVector<TLAbsMessageEntity> getEntities() {
        return this.entities;
    }

    public void setEntities(TLVector<TLAbsMessageEntity> entities) {
        this.entities = entities;
    }

    public TLAbsMessageMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsMessageMedia media) {
        this.media = media;
    }

    public boolean hasMedia() {
        return (this.flags & 512) != 0;
    }

    public boolean isSent() {
        return (this.flags & 2) != 0;
    }

    public boolean isUnread() {
        return (this.flags & 1) != 0;
    }

    @Override
    public int getClassId() {
        return 301019932;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
        StreamingUtils.writeInt(this.date, stream);
        if ((this.flags & 512) != 0) {
            StreamingUtils.writeTLObject(this.media, stream);
        }
        if ((this.flags & 128) != 0) {
            StreamingUtils.writeTLVector(this.entities, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        if ((this.flags & 512) != 0) {
            this.media = (TLAbsMessageMedia)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 128) != 0) {
            this.entities = StreamingUtils.readTLVector(stream, context);
        }
    }

    @Override
    public String toString() {
        return "updateShortSentMessage#11f1331c";
    }
}

