
package org.telegram.api.encrypted.file;

import org.telegram.api.encrypted.file.TLAbsEncryptedFile;

public class TLEncryptedFileEmpty
extends TLAbsEncryptedFile {
    public static final int CLASS_ID = -1038136962;

    @Override
    public int getClassId() {
        return -1038136962;
    }

    @Override
    public String toString() {
        return "encryptedFileEmpty#c21f497e";
    }
}

