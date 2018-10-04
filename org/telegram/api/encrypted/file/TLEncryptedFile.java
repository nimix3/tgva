
package org.telegram.api.encrypted.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.file.TLAbsEncryptedFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLEncryptedFile
extends TLAbsEncryptedFile {
    public static final int CLASS_ID = 1248893260;
    private long accessHash;
    private int size;
    private int dcId;
    private int keyFingerprint;

    @Override
    public int getClassId() {
        return 1248893260;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getDcId() {
        return this.dcId;
    }

    public void setDcId(int dcId) {
        this.dcId = dcId;
    }

    public int getKeyFingerprint() {
        return this.keyFingerprint;
    }

    public void setKeyFingerprint(int keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }

    public long getAccess_hash() {
        return this.accessHash;
    }

    public void setAccess_hash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDc_id() {
        return this.dcId;
    }

    public void setDc_id(int dcId) {
        this.dcId = dcId;
    }

    public int getKey_fingerprint() {
        return this.keyFingerprint;
    }

    public void setKey_fingerprint(int keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeInt(this.size, stream);
        StreamingUtils.writeInt(this.dcId, stream);
        StreamingUtils.writeInt(this.keyFingerprint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readLong(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.size = StreamingUtils.readInt(stream);
        this.dcId = StreamingUtils.readInt(stream);
        this.keyFingerprint = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "encryptedFile#4a70994c";
    }
}

