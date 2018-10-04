
package org.telegram.api.sticker.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;

public class TLStickerPack
extends TLObject {
    public static final int CLASS_ID = 313694676;
    private String emoticon;
    private TLLongVector documents;

    @Override
    public int getClassId() {
        return 313694676;
    }

    public String getEmoticon() {
        return this.emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public TLLongVector getDocuments() {
        return this.documents;
    }

    public void setDocuments(TLLongVector documents) {
        this.documents = documents;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.emoticon, stream);
        StreamingUtils.writeTLVector(this.documents, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.emoticon = StreamingUtils.readTLString(stream);
        this.documents = StreamingUtils.readTLLongVector(stream, context);
    }

    @Override
    public String toString() {
        return "stickerPack#12b299d4";
    }
}

