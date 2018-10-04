
package org.telegram.api.bot.inlinemessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.inlinemessage.TLAbsBotInlineMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLBotInlineMessageMediaAuto
extends TLAbsBotInlineMessage {
    public static final int CLASS_ID = 175419739;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_REPLY_MARKUP = 4;
    private int flags;
    private String caption;
    private TLAbsBotInlineMessage replyMarkup;

    public int getFlags() {
        return this.flags;
    }

    public String getCaption() {
        return this.caption;
    }

    public TLAbsBotInlineMessage getReplyMarkup() {
        return this.replyMarkup;
    }

    @Override
    public int getClassId() {
        return 175419739;
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
            this.replyMarkup = (TLAbsBotInlineMessage)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "botInlineMessageMediaAuto#a74b15b";
    }
}

