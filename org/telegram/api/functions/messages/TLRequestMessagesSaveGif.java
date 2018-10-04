
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.document.TLAbsInputDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesSaveGif
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 846868683;
    private TLAbsInputDocument id;
    private boolean unsave;

    public TLAbsInputDocument getId() {
        return this.id;
    }

    public void setId(TLAbsInputDocument id) {
        this.id = id;
    }

    public boolean isUnsave() {
        return this.unsave;
    }

    public void setUnsave(boolean unsave) {
        this.unsave = unsave;
    }

    @Override
    public int getClassId() {
        return 846868683;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
        StreamingUtils.writeTLBool(this.unsave, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = (TLAbsInputDocument)StreamingUtils.readTLObject(stream, context);
        this.unsave = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "messages.saveGif#327a30cb";
    }
}

