
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessage;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessage
extends TLAbsDecryptedMessage {
    public static final int CLASS_ID = 541931640;
    private int ttl;
    private String message;
    private TLAbsDecryptedMessageMedia media;

    @Override
    public int getClassId() {
        return 541931640;
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TLAbsDecryptedMessageMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsDecryptedMessageMedia media) {
        this.media = media;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.randomId, stream);
        StreamingUtils.writeInt(this.ttl, stream);
        StreamingUtils.writeTLString(this.message, stream);
        StreamingUtils.writeTLObject(this.media, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.randomId = StreamingUtils.readLong(stream);
        this.ttl = StreamingUtils.readInt(stream);
        this.message = StreamingUtils.readTLString(stream);
        this.media = (TLAbsDecryptedMessageMedia)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessage#204d3878";
    }
}

