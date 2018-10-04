
package org.telegram.tl;

import org.telegram.tl.TLVector;

public class TLLongVector
extends TLVector<Long> {
    public TLLongVector() {
        this.setDestClass(Long.class);
    }

    @Override
    public String toString() {
        return "vector<long>#1cb5c415";
    }
}

