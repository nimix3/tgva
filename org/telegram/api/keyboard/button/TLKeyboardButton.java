
package org.telegram.api.keyboard.button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLKeyboardButton
extends TLAbsKeyboardButton {
    public static final int CLASS_ID = -1560655744;
    private String text;

    @Override
    public int getClassId() {
        return -1560655744;
    }

    @Override
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "keyboard.TLKeyboardButton#a2fa4880";
    }
}

