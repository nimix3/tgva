
package org.telegram.mtproto.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.telegram.mtproto.secure.Entropy;
import org.telegram.mtproto.secure.aes.AESImplementation;
import org.telegram.mtproto.secure.aes.DefaultAESImplementation;

public class CryptoUtils {
    private static final ThreadLocal<MessageDigest> md5 = new ThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() {
            MessageDigest crypt = null;
            try {
                crypt = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return crypt;
        }
    };
    private static final ThreadLocal<MessageDigest> sha1 = new ThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() {
            MessageDigest crypt = null;
            try {
                crypt = MessageDigest.getInstance("SHA-1");
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return crypt;
        }
    };
    private static AESImplementation currentImplementation = new DefaultAESImplementation();

    public static void setAESImplementation(AESImplementation implementation) {
        currentImplementation = implementation;
    }

    public static byte[] RSA(byte[] src, BigInteger key, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(key, exponent));
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(1, publicKey);
            return cipher.doFinal(src);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void AES256IGEDecryptBig(byte[] src, byte[] dest, int len, byte[] iv, byte[] key) {
        currentImplementation.AES256IGEDecrypt(src, dest, len, iv, key);
    }

    public static byte[] AES256IGEDecrypt(byte[] src, byte[] iv, byte[] key) {
        byte[] res = new byte[src.length];
        currentImplementation.AES256IGEDecrypt(src, res, src.length, iv, key);
        return res;
    }

    public static void AES256IGEDecrypt(File src, File dest, byte[] iv, byte[] key) throws IOException {
        currentImplementation.AES256IGEDecrypt(src.getAbsolutePath(), dest.getAbsolutePath(), iv, key);
    }

    public static void AES256IGEEncrypt(File src, File dest, byte[] iv, byte[] key) throws IOException {
        currentImplementation.AES256IGEEncrypt(src.getAbsolutePath(), dest.getAbsolutePath(), iv, key);
    }

    public static byte[] AES256IGEEncrypt(byte[] src, byte[] iv, byte[] key) {
        byte[] res = new byte[src.length];
        currentImplementation.AES256IGEEncrypt(src, res, src.length, iv, key);
        return res;
    }

    public static String MD5(byte[] src) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("MD5");
            crypt.reset();
            crypt.update(src);
            return CryptoUtils.ToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MD5(RandomAccessFile randomAccessFile) {
        try {
            MessageDigest crypt = md5.get();
            crypt.reset();
            byte[] block = new byte[8192];
            int i = 0;
            while ((long)i < randomAccessFile.length()) {
                int len = (int)Math.min((long)block.length, randomAccessFile.length() - (long)i);
                randomAccessFile.readFully(block, 0, len);
                crypt.update(block, 0, len);
                i += 8192;
            }
            return CryptoUtils.ToHex(crypt.digest());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MD5(File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            MessageDigest crypt = md5.get();
            crypt.reset();
            byte[] block = new byte[8192];
            int i = 0;
            while ((long)i < randomAccessFile.length()) {
                int len = (int)Math.min((long)block.length, randomAccessFile.length() - (long)i);
                randomAccessFile.readFully(block, 0, len);
                crypt.update(block, 0, len);
                i += 8192;
            }
            return CryptoUtils.ToHex(crypt.digest());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] MD5Raw(byte[] src) {
        MessageDigest crypt = md5.get();
        crypt.reset();
        crypt.update(src);
        return crypt.digest();
    }

    public static String ToHex(byte[] src) {
        String res = "";
        for (int i = 0; i < src.length; ++i) {
            res = res + String.format("%02X", src[i] & 255);
        }
        return res.toLowerCase();
    }

    public static byte[] SHA1(InputStream in) throws IOException {
        int len;
        MessageDigest crypt = sha1.get();
        crypt.reset();
        byte[] buf = new byte[4096];
        while ((len = in.read(buf)) > 0) {
            Thread.yield();
            crypt.update(buf, 0, len);
        }
        in.close();
        return crypt.digest();
    }

    public static byte[] SHA1(String fileName) throws IOException {
        int len;
        MessageDigest crypt = sha1.get();
        crypt.reset();
        FileInputStream in = new FileInputStream(fileName);
        byte[] buf = new byte[4096];
        while ((len = in.read(buf)) > 0) {
            Thread.yield();
            crypt.update(buf, 0, len);
        }
        in.close();
        return crypt.digest();
    }

    public static byte[] SHA1(byte[] src) {
        MessageDigest crypt = sha1.get();
        crypt.reset();
        crypt.update(src);
        return crypt.digest();
    }

    public static /* varargs */ byte[] SHA1(byte[] ... src1) {
        MessageDigest crypt = sha1.get();
        crypt.reset();
        for (int i = 0; i < src1.length; ++i) {
            crypt.update(src1[i]);
        }
        return crypt.digest();
    }

    public static boolean arrayEq(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == b[i]) continue;
            return false;
        }
        return true;
    }

    public static /* varargs */ byte[] concat(byte[] ... v) {
        int len = 0;
        for (int i = 0; i < v.length; ++i) {
            len += v[i].length;
        }
        byte[] res = new byte[len];
        int offset = 0;
        for (int i = 0; i < v.length; ++i) {
            System.arraycopy(v[i], 0, res, offset, v[i].length);
            offset += v[i].length;
        }
        return res;
    }

    public static byte[] substring(byte[] src, int start, int len) {
        byte[] res = new byte[len];
        System.arraycopy(src, start, res, 0, len);
        return res;
    }

    public static byte[] align(byte[] src, int factor) {
        if (src.length % factor == 0) {
            return src;
        }
        int padding = factor - src.length % factor;
        return CryptoUtils.concat(src, Entropy.getInstance().generateSeed(padding));
    }

    public static byte[] alignKeyZero(byte[] src, int size) {
        if (src.length == size) {
            return src;
        }
        if (src.length > size) {
            return CryptoUtils.substring(src, src.length - size, size);
        }
        return CryptoUtils.concat(new byte[size - src.length], src);
    }

    public static byte[] xor(byte[] a, byte[] b) {
        byte[] res = new byte[a.length];
        for (int i = 0; i < a.length; ++i) {
            res[i] = (byte)(a[i] ^ b[i]);
        }
        return res;
    }

    public static BigInteger loadBigInt(byte[] data) {
        return new BigInteger(1, data);
    }

    public static byte[] fromBigInt(BigInteger val) {
        byte[] res = val.toByteArray();
        if (res[0] == 0) {
            byte[] res2 = new byte[res.length - 1];
            System.arraycopy(res, 1, res2, 0, res2.length);
            return res2;
        }
        return res;
    }

    public static boolean isZero(byte[] src) {
        for (int i = 0; i < src.length; ++i) {
            if (src[i] == 0) continue;
            return false;
        }
        return true;
    }

}
