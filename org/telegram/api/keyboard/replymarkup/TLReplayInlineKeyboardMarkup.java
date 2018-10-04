
package org.telegram.api.keyboard.replymarkup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.TLKeyboardButtonRow;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLReplayInlineKeyboardMarkup
extends TLAbsReplyMarkup {
    public static final int CLASS_ID = 1218642516;
    private TLVector<TLKeyboardButtonRow> rows = new TLVector();

    @Override
    public int getClassId() {
        return 1218642516;
    }

    public TLVector<TLKeyboardButtonRow> getRows() {
        return this.rows;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.rows, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.rows = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "replyInlineMarkup#48a30254";
    }
}

