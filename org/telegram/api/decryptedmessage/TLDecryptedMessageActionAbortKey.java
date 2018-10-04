
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionAbortKey
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = -586814357;
    private long exchangeId;

    public TLDecryptedMessageActionAbortKey() {
    }

    public TLDecryptedMessageActionAbortKey(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Override
    public int getClassId() {
        return -586814357;
    }

    public long getExchangeId() {
        return this.exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.exchangeId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.exchangeId = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionAbortKey#dd05ec6b";
    }
}

