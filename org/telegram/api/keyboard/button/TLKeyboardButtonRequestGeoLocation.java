
package org.telegram.api.keyboard.button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLKeyboardButtonRequestGeoLocation
extends TLAbsKeyboardButton {
    public static final int CLASS_ID = -59151553;

    @Override
    public int getClassId() {
        return -59151553;
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
        return "keyboardButtonRequestGeoLocation#fc796b3f";
    }
}

