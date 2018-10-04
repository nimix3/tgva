
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionRequestKey
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = -204906213;
    private long exchangeId;
    private TLBytes g_a;

    public TLDecryptedMessageActionRequestKey() {
    }

    public TLDecryptedMessageActionRequestKey(long exchangeId, TLBytes g_a) {
        this.exchangeId = exchangeId;
        this.g_a = g_a;
    }

    @Override
    public int getClassId() {
        return -204906213;
    }

    public long getExchangeId() {
        return this.exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public TLBytes getG_a() {
        return this.g_a;
    }

    public void setG_a(TLBytes g_a) {
        this.g_a = g_a;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.exchangeId, stream);
        StreamingUtils.writeTLBytes(this.g_a, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.exchangeId = StreamingUtils.readLong(stream);
        this.g_a = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionRequestKey#f3c9611b";
    }
}

