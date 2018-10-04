
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionAcceptKey
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = 1877046107;
    private long exchangeId;
    private TLBytes g_a;
    private long KeyFingerprint;

    public TLDecryptedMessageActionAcceptKey() {
    }

    public TLDecryptedMessageActionAcceptKey(long exchangeId, TLBytes g_a, long KeyFingerprint) {
        this.exchangeId = exchangeId;
        this.g_a = g_a;
        this.KeyFingerprint = KeyFingerprint;
    }

    @Override
    public int getClassId() {
        return 1877046107;
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

    public long getKeyFingerprint() {
        return this.KeyFingerprint;
    }

    public void setKeyFingerprint(long keyFingerprint) {
        this.KeyFingerprint = keyFingerprint;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.exchangeId, stream);
        StreamingUtils.writeTLBytes(this.g_a, stream);
        StreamingUtils.writeLong(this.KeyFingerprint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.exchangeId = StreamingUtils.readLong(stream);
        this.g_a = StreamingUtils.readTLBytes(stream, context);
        this.KeyFingerprint = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionAcceptKey#6fe1735b";
    }
}

