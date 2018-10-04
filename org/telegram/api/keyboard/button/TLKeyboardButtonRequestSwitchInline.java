
package org.telegram.api.keyboard.button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLKeyboardButtonRequestSwitchInline
extends TLAbsKeyboardButton {
    public static final int CLASS_ID = -59151553;
    private String query;

    @Override
    public int getClassId() {
        return -59151553;
    }

    public String getQuery() {
        return this.query;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
        StreamingUtils.writeTLString(this.query, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
        this.query = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "keyboardButtonSwitchInline#ea1b7a14";
    }
}

