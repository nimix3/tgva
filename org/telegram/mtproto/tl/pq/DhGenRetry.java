
package org.telegram.mtproto.tl.pq;

import org.telegram.mtproto.tl.pq.DhGenResult;

public class DhGenRetry
extends DhGenResult {
    public static final int CLASS_ID = 1188831161;

    public DhGenRetry(byte[] nonce, byte[] serverNonce, byte[] newNonceHash) {
        super(nonce, serverNonce, newNonceHash);
    }

    public DhGenRetry() {
    }

    @Override
    public int getClassId() {
        return 1188831161;
    }

    @Override
    public String toString() {
        return "dh_gen_retry#46dc1fb9";
    }
}

