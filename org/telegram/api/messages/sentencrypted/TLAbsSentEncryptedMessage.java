
package org.telegram.api.messages.sentencrypted;

import org.telegram.api.encrypted.file.TLAbsEncryptedFile;
import org.telegram.tl.TLObject;

public abstract class TLAbsSentEncryptedMessage
extends TLObject {
    protected int date;
    protected TLAbsEncryptedFile file;

    protected TLAbsSentEncryptedMessage() {
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    public TLAbsEncryptedFile getFile() {
        return this.file;
    }

    public void setFile(TLAbsEncryptedFile file) {
        this.file = file;
    }
}

