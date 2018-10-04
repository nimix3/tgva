
package org.telegram.api.messages.savedgifs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLSavedGifs
extends TLObject {
    public static final int CLASS_ID = 772213157;
    private int hash;
    private TLVector<TLAbsDocument> gifs;

    public int getHash() {
        return this.hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public TLVector<TLAbsDocument> getGifs() {
        return this.gifs;
    }

    public void setGifs(TLVector<TLAbsDocument> gifs) {
        this.gifs = gifs;
    }

    @Override
    public int getClassId() {
        return 772213157;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.hash, stream);
        StreamingUtils.writeTLVector(this.gifs, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readInt(stream);
        this.gifs = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "savedgifs#2e0709a5";
    }
}

