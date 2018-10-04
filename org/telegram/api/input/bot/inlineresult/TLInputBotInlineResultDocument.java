
package org.telegram.api.input.bot.inlineresult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.inlinemessage.TLAbsInputBotInlineMessage;
import org.telegram.api.input.bot.inlineresult.TLAbsInputBotInlineResult;
import org.telegram.api.input.document.TLAbsInputDocument;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputBotInlineResultDocument
extends TLAbsInputBotInlineResult {
    public static final int CLASS_ID = -459324;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_TITLE = 2;
    private static final int FLAG_DESCRIPTION = 4;
    private static final int FLAG_UNUSED3 = 8;
    private static final int FLAG_UNUSED4 = 16;
    private static final int FLAG_UNUSED5 = 32;
    private static final int FLAG_UNUSED6 = 64;
    private static final int FLAG_UNUSED7 = 128;
    private int flags;
    private String id;
    private String type;
    private String title;
    private String description;
    private TLAbsInputDocument document;
    private TLAbsInputBotInlineMessage sendMessage;

    @Override
    public int getClassId() {
        return -459324;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getFlags() {
        return this.flags;
    }

    public TLAbsInputDocument getDocument() {
        return this.document;
    }

    public TLAbsInputBotInlineMessage getSendMessage() {
        return this.sendMessage;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.id, stream);
        StreamingUtils.writeTLString(this.type, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.title, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.description, stream);
        }
        StreamingUtils.writeTLObject(this.document, stream);
        StreamingUtils.writeTLObject(this.sendMessage, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readTLString(stream);
        this.type = StreamingUtils.readTLString(stream);
        if ((this.flags & 2) != 0) {
            this.title = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.description = StreamingUtils.readTLString(stream);
        }
        this.document = (TLAbsInputDocument)StreamingUtils.readTLObject(stream, context);
        this.sendMessage = (TLAbsInputBotInlineMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputBotInlineResultDocument#fff8fdc4";
    }
}

