
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLInputMediaUploadedThumbDocument
extends TLAbsInputMedia {
    public static final int CLASS_ID = -1386138479;
    private TLAbsInputFile file;
    private TLAbsInputFile thumb;
    private String mimeType;
    private TLVector<TLAbsDocumentAttribute> attributes;
    private String caption;

    @Override
    public int getClassId() {
        return -1386138479;
    }

    public TLAbsInputFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsInputFile file) {
        this.file = file;
    }

    public TLAbsInputFile getThumb() {
        return this.thumb;
    }

    public void setThumb(TLAbsInputFile thumb) {
        this.thumb = thumb;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public TLVector<TLAbsDocumentAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(TLVector<TLAbsDocumentAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.file, stream);
        StreamingUtils.writeTLObject(this.thumb, stream);
        StreamingUtils.writeTLString(this.mimeType, stream);
        StreamingUtils.writeTLVector(this.attributes, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.file = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.thumb = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.mimeType = StreamingUtils.readTLString(stream);
        this.attributes = StreamingUtils.readTLVector(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaUploadedThumbDocument#ad613491";
    }
}

