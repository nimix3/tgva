
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetDocumentByHash
extends TLMethod<TLAbsDocument> {
    public static final int CLASS_ID = 864953444;
    private TLBytes sha256;
    private int size;
    private String mimeType;

    @Override
    public int getClassId() {
        return 864953444;
    }

    @Override
    public TLAbsDocument deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsDocument) {
            return (TLAbsDocument)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsDocument.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLBytes getSha256() {
        return this.sha256;
    }

    public void setSha256(TLBytes sha256) {
        this.sha256 = sha256;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.sha256, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeTLString(this.mimeType, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.sha256 = StreamingUtils.readTLBytes(stream, context);
        this.size = StreamingUtils.readInt(stream);
        this.mimeType = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messages.getDocumentByHash#338e2464";
    }
}

