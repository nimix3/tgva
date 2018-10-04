
package org.telegram.api.foundgif;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.api.foundgif.TLAbsFoundGif;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLFoundGifCached
extends TLAbsFoundGif {
    public static final int CLASS_ID = -1670052855;
    private String url;
    private TLAbsPhoto photo;
    private TLAbsDocument document;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsPhoto photo) {
        this.photo = photo;
    }

    public TLAbsDocument getDocument() {
        return this.document;
    }

    public void setDocument(TLAbsDocument document) {
        this.document = document;
    }

    @Override
    public int getClassId() {
        return -1670052855;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.url, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLObject(this.document, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.url = StreamingUtils.readTLString(stream);
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        this.document = (TLAbsDocument)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "foundGifCached#9c750409";
    }
}

