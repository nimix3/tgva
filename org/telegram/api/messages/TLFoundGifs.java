
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.foundgif.TLAbsFoundGif;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLFoundGifs
extends TLObject {
    public static final int CLASS_ID = 1158290442;
    private int nextOffset;
    private TLVector<TLAbsFoundGif> results;

    @Override
    public int getClassId() {
        return 1158290442;
    }

    public int getNextOffset() {
        return this.nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public TLVector<TLAbsFoundGif> getResults() {
        return this.results;
    }

    public void setResults(TLVector<TLAbsFoundGif> results) {
        this.results = results;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.nextOffset, stream);
        StreamingUtils.writeTLVector(this.results, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.nextOffset = StreamingUtils.readInt(stream);
        this.results = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.foundGifs#450a1c0a";
    }
}

