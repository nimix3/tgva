
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageMediaAudio
extends TLAbsDecryptedMessageMedia {
    public static final int CLASS_ID = 1474341323;
    private int duration;
    private String mimeType;
    private int size;
    private TLBytes key;
    private TLBytes iv;

    @Override
    public int getClassId() {
        return 1474341323;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
        StreamingUtils.writeInt(this.duration, stream);
        StreamingUtils.writeTLString(this.mimeType, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeTLBytes(this.key, stream);
        StreamingUtils.writeTLBytes(this.iv, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.duration = StreamingUtils.readInt(stream);
        this.mimeType = StreamingUtils.readTLString(stream);
        this.size = StreamingUtils.readInt(stream);
        this.key = StreamingUtils.readTLBytes(stream, context);
        this.iv = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageMediaAudio#57e0a9cb";
    }
}

