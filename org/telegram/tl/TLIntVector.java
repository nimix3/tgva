
package org.telegram.tl;

import org.telegram.tl.TLVector;

public class TLIntVector
extends TLVector<Integer> {
    public TLIntVector() {
        this.setDestClass(Integer.class);
    }

    @Override
    public String toString() {
        return "vector<int>#1cb5c415";
    }

    public int[] toIntArray() {
        int[] res = new int[this.size()];
        for (int i = 0; i < res.length; ++i) {
            res[i] = (Integer)this.get(i);
        }
        return res;
    }
}

