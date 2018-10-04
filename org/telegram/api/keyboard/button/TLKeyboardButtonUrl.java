
package org.telegram.api.keyboard.button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLKeyboardButtonUrl
extends TLAbsKeyboardButton {
    public static final int CLASS_ID = 629866245;
    private String url;

    @Override
    public int getClassId() {
        return 629866245;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
        StreamingUtils.writeTLString(this.url, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
        this.url = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "keyboardButtonUrl#258aff05";
    }
}

