
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLDecryptedMessageMediaExternalDocument
extends TLAbsDecryptedMessageMedia {
    public static final int CLASS_ID = -90853155;
    private long id;
    private long accessHash;
    private int date;
    private String mimetype;
    private int size;
    private TLAbsPhotoSize thumb;
    private int dcId;
    private TLVector<TLAbsDocumentAttribute> attributes = new TLVector();

    @Override
    public int getClassId() {
        return -90853155;
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

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getMimetype() {
        return this.mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
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
        StreamingUtils.writeTLString(this.mimetype, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeTLObject(this.thumb, stream);
        StreamingUtils.writeInt(this.dcId, stream);
        StreamingUtils.writeTLVector(this.attributes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.date = StreamingUtils.readInt(stream);
        this.mimetype = StreamingUtils.readTLString(stream);
        this.size = StreamingUtils.readInt(stream);
        this.thumb = (TLAbsPhotoSize)StreamingUtils.readTLObject(stream, context);
        this.dcId = StreamingUtils.readInt(stream);
        this.attributes = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageMediaExternalDocument#fa95b0dd";
    }
}

