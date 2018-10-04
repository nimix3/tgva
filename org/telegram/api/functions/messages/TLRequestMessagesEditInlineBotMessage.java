
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.TLInputBotInlineMessageId;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesEditInlineBotMessage
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 319564933;
    private static final int FLAG_UNUSED_0 = 1;
    private static final int FLAG_NO_WEBPREVIEW = 2;
    private static final int FLAG_REPLY_MARKUP = 4;
    private static final int FLAG_ENTITIES = 8;
    private static final int FLAG_UNUSED_4 = 16;
    private static final int FLAG_UNUSED_5 = 32;
    private static final int FLAG_UNUSED_6 = 64;
    private static final int FLAG_UNUSED_7 = 128;
    private static final int FLAG_UNUSED_8 = 256;
    private static final int FLAG_UNUSED_9 = 512;
    private static final int FLAG_UNUSED_10 = 1024;
    private static final int FLAG_MESSAGE = 2048;
    private int flags;
    private TLInputBotInlineMessageId id;
    private String message;
    private TLAbsReplyMarkup replyMarkup;
    private TLVector<TLAbsMessageEntity> entities;

    @Override
    public int getClassId() {
        return 319564933;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLInputBotInlineMessageId getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    public TLVector<TLAbsMessageEntity> getEntities() {
        return this.entities;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.id, stream);
        if ((this.flags & 2048) != 0) {
            StreamingUtils.writeTLString(this.message, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLVector(this.entities, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = (TLInputBotInlineMessageId)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 2048) != 0) {
            this.message = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 8) != 0) {
            this.entities = StreamingUtils.readTLVector(stream, context);
        }
    }

    @Override
    public String toString() {
        return "messages.editInlineBotMessage#130c2c85";
    }
}

