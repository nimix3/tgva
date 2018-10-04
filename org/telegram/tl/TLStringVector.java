
package org.telegram.tl;

import org.telegram.tl.TLVector;

public class TLStringVector
extends TLVector<String> {
    public TLStringVector() {
        this.setDestClass(String.class);
    }

    @Override
    public String toString() {
        return "vector<string>#1cb5c415";
    }
}

