
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionSetMessageTTL
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = -1586283796;
    private int ttlSeconds;

    public TLDecryptedMessageActionSetMessageTTL() {
    }

    public TLDecryptedMessageActionSetMessageTTL(int _ttlSeconds) {
        this.ttlSeconds = _ttlSeconds;
    }

    @Override
    public int getClassId() {
        return -1586283796;
    }

    public int getTtlSeconds() {
        return this.ttlSeconds;
    }

    public void setTtlSeconds(int ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.ttlSeconds, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.ttlSeconds = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionSetMessageTTL#a1733aec";
    }
}

