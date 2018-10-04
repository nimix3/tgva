
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageMediaPhoto
extends TLAbsDecryptedMessageMedia {
    public static final int CLASS_ID = 846826124;
    private TLBytes thumb;
    private int thumbW;
    private int thumbH;
    private int w;
    private int h;
    private int size;
    private TLBytes key;
    private TLBytes iv;

    @Override
    public int getClassId() {
        return 846826124;
    }

    public TLBytes getThumb() {
        return this.thumb;
    }

    public void setThumb(TLBytes thumb) {
        this.thumb = thumb;
    }

    public int getThumbW() {
        return this.thumbW;
    }

    public void setThumbW(int thumbW) {
        this.thumbW = thumbW;
    }

    public int getThumbH() {
        return this.thumbH;
    }

    public void setThumbH(int thumbH) {
        this.thumbH = thumbH;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TLBytes getKey() {
        return this.key;
    }

    public void setKey(TLBytes key) {
        this.key = key;
    }

    public TLBytes getIv() {
        return this.iv;
    }

    public void setIv(TLBytes iv) {
        this.iv = iv;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.thumb, stream);
        StreamingUtils.writeInt(this.thumbW, stream);
        StreamingUtils.writeInt(this.thumbH, stream);
        StreamingUtils.writeInt(this.w, stream);
        StreamingUtils.writeInt(this.h, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeTLBytes(this.key, stream);
        StreamingUtils.writeTLBytes(this.iv, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.thumb = StreamingUtils.readTLBytes(stream, context);
        this.thumbW = StreamingUtils.readInt(stream);
        this.thumbH = StreamingUtils.readInt(stream);
        this.w = StreamingUtils.readInt(stream);
        this.h = StreamingUtils.readInt(stream);
        this.size = StreamingUtils.readInt(stream);
        this.key = StreamingUtils.readTLBytes(stream, context);
        this.iv = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageMediaPhoto#32798a8c";
    }
}

