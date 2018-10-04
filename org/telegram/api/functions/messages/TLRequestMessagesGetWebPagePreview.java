
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetWebPagePreview
extends TLMethod<TLAbsMessageMedia> {
    public static final int CLASS_ID = 623001124;
    private String message = "";

    @Override
    public int getClassId() {
        return 623001124;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public TLAbsMessageMedia deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsMessageMedia) {
            return (TLAbsMessageMedia)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.message.media.TLAbsMessageMedia, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.message = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "account.getWebPreview#25223e24";
    }
}

