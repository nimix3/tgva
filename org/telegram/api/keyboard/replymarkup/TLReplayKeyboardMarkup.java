
package org.telegram.api.keyboard.replymarkup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.TLKeyboardButtonRow;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLReplayKeyboardMarkup
extends TLAbsReplyMarkup {
    public static final int CLASS_ID = 889353612;
    private static final int FLAG_RESIZE = 1;
    private static final int FLAG_SINGLEUSE = 2;
    private static final int FLAG_SELECTIVE = 4;
    private TLVector<TLKeyboardButtonRow> rows = new TLVector();
    protected int flags;

    @Override
    public int getClassId() {
        return 889353612;
    }

    public TLVector<TLKeyboardButtonRow> getRows() {
        return this.rows;
    }

    public int getFlags() {
        return this.flags;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLVector(this.rows, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.rows = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "keyboard.ReplyKeyboardMarkup#3502758c";
    }
}

