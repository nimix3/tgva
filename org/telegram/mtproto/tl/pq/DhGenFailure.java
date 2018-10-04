
package org.telegram.mtproto.tl.pq;

import org.telegram.mtproto.tl.pq.DhGenResult;

public class DhGenFailure
extends DhGenResult {
    public static final int CLASS_ID = -1499615742;

    public DhGenFailure(byte[] nonce, byte[] serverNonce, byte[] newNonceHash) {
        super(nonce, serverNonce, newNonceHash);
    }

    public DhGenFailure() {
    }

    @Override
    public int getClassId() {
        return -1499615742;
    }

    @Override
    public String toString() {
        return "dh_gen_fail#a69dae02";
    }
}

