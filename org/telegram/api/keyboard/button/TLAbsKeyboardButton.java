
package org.telegram.api.keyboard.button;

import org.telegram.tl.TLObject;

public abstract class TLAbsKeyboardButton
extends TLObject {
    protected String text;

    TLAbsKeyboardButton() {
    }

    public String getText() {
        return this.text;
    }
}

