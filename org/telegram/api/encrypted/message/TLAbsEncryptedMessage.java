
package org.telegram.api.encrypted.message;

import org.telegram.api.encrypted.file.TLAbsEncryptedFile;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLObject;

public abstract class TLAbsEncryptedMessage
extends TLObject {
    protected long randomId;
    protected int chatId;
    protected int date;
    protected TLBytes bytes;
    protected TLAbsEncryptedFile file;

    protected TLAbsEncryptedMessage() {
    }

    public long getRandomId() {
        return this.randomId;
    }

    public void setRandomId(long value) {
        this.randomId = value;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int value) {
        this.chatId = value;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    public TLBytes getBytes() {
        return this.bytes;
    }

    public void setBytes(TLBytes value) {
        this.bytes = value;
    }

    public TLAbsEncryptedFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsEncryptedFile file) {
        this.file = file;
    }
}

