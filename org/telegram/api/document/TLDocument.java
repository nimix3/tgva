
package org.telegram.api.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDocument
extends TLAbsDocument {
    public static final int CLASS_ID = -106717361;
    private long accessHash;
    private int date;
    private String mimeType;
    private int size;
    private TLAbsPhotoSize thumb;
    private int dcId;
    private TLVector<TLAbsDocumentAttribute> attributes = new TLVector();

    @Override
    public int getClassId() {
        return -106717361;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
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

    public TLAbsPhotoSize getThumb() {
        return this.thumb;
    }

    public void setThumb(TLAbsPhotoSize thumb) {
        this.thumb = thumb;
    }

    public int getDcId() {
        return this.dcId;
    }

    public void setDcId(int dcId) {
        this.dcId = dcId;
    }

    public TLVector<TLAbsDocumentAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(TLVector<TLAbsDocumentAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLString(this.mimeType, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeTLObject(this.thumb, stream);
        StreamingUtils.writeInt(this.dcId, stream);
        StreamingUtils.writeTLVector(this.attributes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
        this.mimeType = StreamingUtils.readTLString(stream);
        this.size = StreamingUtils.readInt(stream);
        this.thumb = (TLAbsPhotoSize)StreamingUtils.readTLObject(stream, context);
        this.dcId = StreamingUtils.readInt(stream);
        this.attributes = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "document#f9a39f4f";
    }
}

