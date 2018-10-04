
package org.telegram.api.input.bot.inlinemessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.inlinemessage.TLAbsInputBotInlineMessage;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputBotInlineMessageMediaAuto
extends TLAbsInputBotInlineMessage {
    public static final int CLASS_ID = 776201607;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_REPLY_MARKUP = 4;
    private int flags;
    private String caption;
    private TLAbsReplyMarkup replyMarkup;

    public String getCaption() {
        return this.caption;
    }

    public int getFlags() {
        return this.flags;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    @Override
    public int getClassId() {
        return 776201607;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.caption, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.caption = StreamingUtils.readTLString(stream);
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "inputBotInlineMessageMediaAuto#292fed13";
    }
}

