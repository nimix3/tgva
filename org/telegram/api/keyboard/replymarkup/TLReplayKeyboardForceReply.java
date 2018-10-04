
package org.telegram.api.keyboard.replymarkup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLReplayKeyboardForceReply
extends TLAbsReplyMarkup {
    public static final int CLASS_ID = -200242528;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_SINGLEUSE = 2;
    private static final int FLAG_SELECTIVE = 4;
    protected int flags;

    @Override
    public int getClassId() {
        return -200242528;
    }

    public int getFlags() {
        return this.flags;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "keyboard#ReplyKeyboardForceReply#f4108aa0";
    }
}

