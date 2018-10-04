
package org.telegram.mtproto.tl.pq;

import org.telegram.mtproto.tl.pq.DhGenResult;

public class DhGenOk
extends DhGenResult {
    public static final int CLASS_ID = 1003222836;

    public DhGenOk(byte[] nonce, byte[] serverNonce, byte[] newNonceHash) {
        super(nonce, serverNonce, newNonceHash);
    }

    public DhGenOk() {
    }

    @Override
    public int getClassId() {
        return 1003222836;
    }

    @Override
    public String toString() {
        return "dh_gen_ok#3bcbf734";
    }
}

