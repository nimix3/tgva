
package org.telegram.api.keyboard.button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLKeyboardButtonCallback
extends TLAbsKeyboardButton {
    public static final int CLASS_ID = 1748655686;
    private TLBytes data;

    @Override
    public int getClassId() {
        return 1748655686;
    }

    public TLBytes getData() {
        return this.data;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
        StreamingUtils.writeTLBytes(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
        this.data = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "keyboardButtonCallback#683a5e46";
    }
}

