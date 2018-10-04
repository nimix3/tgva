
package org.telegram.mtproto.secure.aes;

import java.io.IOException;

public interface AESImplementation {
    public void AES256IGEDecrypt(byte[] var1, byte[] var2, int var3, byte[] var4, byte[] var5);

    public void AES256IGEEncrypt(byte[] var1, byte[] var2, int var3, byte[] var4, byte[] var5);

    public void AES256IGEEncrypt(String var1, String var2, byte[] var3, byte[] var4) throws IOException;

    public void AES256IGEDecrypt(String var1, String var2, byte[] var3, byte[] var4) throws IOException;
}

