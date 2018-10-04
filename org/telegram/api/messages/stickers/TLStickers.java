
package org.telegram.api.messages.stickers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLDocument;
import org.telegram.api.messages.stickers.TLAbsStickers;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLStickers
extends TLAbsStickers {
    public static final int CLASS_ID = -1970352846;
    private String hash;
    private TLVector<TLDocument> documents;

    @Override
    public int getClassId() {
        return -1970352846;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public TLVector<TLDocument> getDocuments() {
        return this.documents;
    }

    public void setDocuments(TLVector<TLDocument> documents) {
        this.documents = documents;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.hash, stream);
        StreamingUtils.writeTLVector(this.documents, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readTLString(stream);
        this.documents = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "stickers#8a8ecd32";
    }
}

