
package org.telegram.api.bot.inlinemessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.inlinemessage.TLAbsBotInlineMessage;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLBotInlineMessageText
extends TLAbsBotInlineMessage {
    public static final int CLASS_ID = -1937807902;
    private static final int FLAG_NO_WEBPAGE = 1;
    private static final int FLAG_ENTITIES = 2;
    private static final int FLAG_REPLYMARKUP = 4;
    private int flags;
    private String message;
    private TLVector<TLAbsMessageEntity> entites;
    private TLAbsReplyMarkup replyMarkup;

    public int getFlags() {
        return this.flags;
    }

    public String getMessage() {
        return this.message;
    }

    public TLVector<TLAbsMessageEntity> getEntites() {
        return this.entites;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    public boolean hasWebPreview() {
        return (this.flags & 1) == 0;
    }

    @Override
    public int getClassId() {
        return -1937807902;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.message, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLVector(this.entites, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.message = StreamingUtils.readTLString(stream);
        if ((this.flags & 2) != 0) {
            this.entites = StreamingUtils.readTLVector(stream, context);
        }
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "botInlineMessageText#8c7f65e2";
    }
}

