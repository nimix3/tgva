
package org.telegram.api.document.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDocumentAttributeAudio
extends TLAbsDocumentAttribute {
    public static final int CLASS_ID = -1739392570;
    private static final int FLAG_TITLE = 1;
    private static final int FLAG_PERFORMER = 2;
    private static final int FLAG_WAVEFORM = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_UNUSED4 = 16;
    private static final int FLAG_UNUSED5 = 32;
    private static final int FLAG_UNUSED6 = 64;
    private static final int FLAG_UNUSED7 = 128;
    private static final int FLAG_UNUSED8 = 256;
    private static final int FLAG_UNUSED9 = 512;
    private static final int FLAG_VOICE = 1024;
    private int flags;
    private int duration;
    private String title;
    private String performer;
    private TLBytes waveform;

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return this.performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public TLBytes getWaveform() {
        return this.waveform;
    }

    public void setWaveform(TLBytes waveform) {
        this.waveform = waveform;
    }

    public boolean isVoice() {
        return (this.flags & 1024) != 0;
    }

    @Override
    public int getClassId() {
        return -1739392570;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.duration, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLString(this.title, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.performer, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLBytes(this.waveform, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.duration = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.title = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 2) != 0) {
            this.performer = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.waveform = StreamingUtils.readTLBytes(stream, context);
        }
    }

    @Override
    public String toString() {
        return "documentAttributeAudio#9852f9c6";
    }
}

