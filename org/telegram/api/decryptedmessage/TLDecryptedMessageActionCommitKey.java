
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionCommitKey
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = -332526693;
    private long exchangeId;
    private long keyFingerprint;

    public TLDecryptedMessageActionCommitKey() {
    }

    public TLDecryptedMessageActionCommitKey(long exchangeId, long keyFingerprint) {
        this.exchangeId = exchangeId;
        this.keyFingerprint = keyFingerprint;
    }

    @Override
    public int getClassId() {
        return -332526693;
    }

    public long getExchangeId() {
        return this.exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public long getKeyFingerprint() {
        return this.keyFingerprint;
    }

    public void setKeyFingerprint(long keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.exchangeId, stream);
        StreamingUtils.writeLong(this.keyFingerprint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.exchangeId = StreamingUtils.readLong(stream);
        this.keyFingerprint = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageActionCommitKey#ec2e0b9b";
    }
}

