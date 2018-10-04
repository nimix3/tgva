
package org.telegram.api.sticker.set;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.sticker.set.TLAbsStickerSet;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLStickerSet
extends TLAbsStickerSet {
    public static final int CLASS_ID = -852477119;
    public static final int FLAG_INSTALLED = 1;
    public static final int FLAG_DISABLED = 2;
    public static final int FLAG_OFFICIAL = 4;
    private int flags;
    private long id;
    private long accessHash;
    private String title;
    private String shortName;
    private int count;
    private int hash;

    @Override
    public int getClassId() {
        return -852477119;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHash() {
        return this.hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.shortName, stream);
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeInt(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.shortName = StreamingUtils.readTLString(stream);
        this.count = StreamingUtils.readInt(stream);
        this.hash = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "stickers.set.StickerSet#cd303b41";
    }
}

