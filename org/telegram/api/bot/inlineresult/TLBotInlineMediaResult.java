
package org.telegram.api.bot.inlineresult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.inlinemessage.TLAbsBotInlineMessage;
import org.telegram.api.bot.inlineresult.TLAbsBotInlineResult;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLBotInlineMediaResult
extends TLAbsBotInlineResult {
    public static final int CLASS_ID = 400266251;
    private static final int FLAG_PHOTO = 1;
    private static final int FLAG_DOCUMENT = 2;
    private static final int FLAG_TITLE = 4;
    private static final int FLAG_DESCRIPTION = 8;
    private int flags;
    private String id;
    private String type;
    private TLAbsPhoto photo;
    private TLAbsDocument document;
    private String title;
    private String description;
    private TLAbsBotInlineMessage sendMessage;

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public TLAbsBotInlineMessage getSendMessage() {
        return this.sendMessage;
    }

    public int getFlags() {
        return this.flags;
    }

    public TLAbsDocument getDocument() {
        return this.document;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public int getClassId() {
        return 400266251;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.id, stream);
        StreamingUtils.writeTLString(this.type, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.photo, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLObject(this.photo, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.title, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLString(this.title, stream);
        }
        StreamingUtils.writeTLObject(this.sendMessage, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readTLString(stream);
        this.type = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 2) != 0) {
            this.document = (TLAbsDocument)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 4) != 0) {
            this.title = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 8) != 0) {
            this.description = StreamingUtils.readTLString(stream);
        }
        this.sendMessage = (TLAbsBotInlineMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "botInlineMediaResult#17db940b";
    }
}

