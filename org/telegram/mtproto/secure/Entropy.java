
package org.telegram.mtproto.secure;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.telegram.mtproto.secure.CryptoUtils;

public final class Entropy {
    private static SecureRandom random;
    private static Entropy instance;

    private Entropy() {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
    }

    public static Entropy getInstance() {
        if (instance == null) {
            instance = new Entropy();
        }
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] generateSeed(int size) {
        SecureRandom secureRandom = random;
        synchronized (secureRandom) {
            return random.generateSeed(size);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] generateSeed(byte[] sourceSeed) {
        SecureRandom secureRandom = random;
        synchronized (secureRandom) {
            return CryptoUtils.xor(random.generateSeed(sourceSeed.length), sourceSeed);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long generateRandomId() {
        SecureRandom secureRandom = random;
        synchronized (secureRandom) {
            return random.nextLong();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int randomInt() {
        SecureRandom secureRandom = random;
        synchronized (secureRandom) {
            return random.nextInt();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void feedEntropy(byte[] data) {
        SecureRandom secureRandom = random;
        synchronized (secureRandom) {
            random.setSeed(data);
        }
    }
}

