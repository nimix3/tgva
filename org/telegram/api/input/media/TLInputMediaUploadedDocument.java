
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

public class TLInputMediaUploadedDocument
extends TLAbsInputMedia {
    public static final int CLASS_ID = 495530093;
    private TLAbsInputFile file;
    private String mimeType = "";
    private TLVector<TLAbsDocumentAttribute> attributes = new TLVector();
    private String caption;

    @Override
    public int getClassId() {
        return 495530093;
    }

    public TLAbsInputFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsInputFile file) {
        this.file = file;
    }

    public TLVector<TLAbsDocumentAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(TLVector<TLAbsDocumentAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
        StreamingUtils.writeTLString(this.mimeType, stream);
        StreamingUtils.writeTLObject(this.attributes, stream);
        StreamingUtils.writeTLString(this.caption, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.file = (TLAbsInputFile)StreamingUtils.readTLObject(stream, context);
        this.mimeType = StreamingUtils.readTLString(stream);
        this.attributes = (TLVector)StreamingUtils.readTLObject(stream, context);
        this.caption = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputMediaUploadedDocument#1d89306d";
    }
}

