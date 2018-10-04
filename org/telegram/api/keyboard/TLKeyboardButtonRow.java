
package org.telegram.api.keyboard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.button.TLAbsKeyboardButton;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLKeyboardButtonRow
extends TLObject {
    public static final int CLASS_ID = 2002815875;
    public TLVector<TLAbsKeyboardButton> buttons = new TLVector();

    @Override
    public int getClassId() {
        return 2002815875;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.buttons, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.buttons = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "keyboard.KeyboardButtonRow#77608b83";
    }
}

