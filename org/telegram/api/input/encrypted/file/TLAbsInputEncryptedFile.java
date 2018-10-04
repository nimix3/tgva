
package org.telegram.api.input.encrypted.file;

import org.telegram.tl.TLBytes;
import org.telegram.tl.TLObject;

public abstract class TLAbsInputEncryptedFile
extends TLObject {
    public long id;
    public long accessHash;
    public int parts;
    public int keyFingerprint;
    public String md5Checksum;
    public TLBytes key;
    public TLBytes iv;

    protected TLAbsInputEncryptedFile() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getParts() {
        return this.parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public int getKeyFingerprint() {
        return this.keyFingerprint;
    }

    public void setKeyFingerprint(int keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }

    public TLBytes getKey() {
        return this.key;
    }

    public void setKey(TLBytes key) {
        this.key = key;
    }

    public TLBytes getIv() {
        return this.iv;
    }

    public void setIv(TLBytes iv) {
        this.iv = iv;
    }
}

