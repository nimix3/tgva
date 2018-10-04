
package org.telegram.api.input.bot.inlineresult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.inlinemessage.TLAbsInputBotInlineMessage;
import org.telegram.api.input.bot.inlineresult.TLAbsInputBotInlineResult;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputBotInlineResultPhoto
extends TLAbsInputBotInlineResult {
    public static final int CLASS_ID = -1462213465;
    private String id;
    private String type;
    private TLAbsInputPhoto photo;
    private TLAbsInputBotInlineMessage sendMessage;

    @Override
    public int getClassId() {
        return -1462213465;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public TLAbsInputPhoto getPhoto() {
        return this.photo;
    }

    public TLAbsInputBotInlineMessage getSendMessage() {
        return this.sendMessage;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.id, stream);
        StreamingUtils.writeTLString(this.type, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLObject(this.sendMessage, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readTLString(stream);
        this.type = StreamingUtils.readTLString(stream);
        this.photo = (TLAbsInputPhoto)StreamingUtils.readTLObject(stream, context);
        this.sendMessage = (TLAbsInputBotInlineMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputBotInlineResultPhoto#a8d864a7";
    }
}

