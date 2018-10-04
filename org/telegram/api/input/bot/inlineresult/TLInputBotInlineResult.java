
package org.telegram.api.input.bot.inlineresult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.inlinemessage.TLAbsInputBotInlineMessage;
import org.telegram.api.input.bot.inlineresult.TLAbsInputBotInlineResult;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputBotInlineResult
extends TLAbsInputBotInlineResult {
    public static final int CLASS_ID = 750510426;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_TITLE = 2;
    private static final int FLAG_DESCRIPTION = 4;
    private static final int FLAG_URL = 8;
    private static final int FLAG_THUMB_URL = 16;
    private static final int FLAG_CONTENT = 32;
    private static final int FLAG_SIZE = 64;
    private static final int FLAG_DURATION = 128;
    private int flags;
    private String id;
    private String type;
    private String title;
    private String description;
    private String url;
    private String thumbUrl;
    private String contentUrl;
    private String contentType;
    private int w;
    private int h;
    private int duration;
    private TLAbsInputBotInlineMessage sendMessage;

    @Override
    public int getClassId() {
        return 750510426;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getContentUrl() {
        return this.contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public TLAbsInputBotInlineMessage getSendMessage() {
        return this.sendMessage;
    }

    public void setSendMessage(TLAbsInputBotInlineMessage sendMessage) {
        this.sendMessage = sendMessage;
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
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLString(this.url, stream);
        }
        if ((this.flags & 16) != 0) {
            StreamingUtils.writeTLString(this.thumbUrl, stream);
        }
        if ((this.flags & 32) != 0) {
            StreamingUtils.writeTLString(this.contentUrl, stream);
            StreamingUtils.writeTLString(this.contentType, stream);
        }
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeInt(this.w, stream);
            StreamingUtils.writeInt(this.h, stream);
        }
        if ((this.flags & 128) != 0) {
            StreamingUtils.writeInt(this.duration, stream);
        }
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
        if ((this.flags & 8) != 0) {
            this.url = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 16) != 0) {
            this.thumbUrl = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 32) != 0) {
            this.contentUrl = StreamingUtils.readTLString(stream);
            this.contentType = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 64) != 0) {
            this.w = StreamingUtils.readInt(stream);
            this.h = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 128) != 0) {
            this.duration = StreamingUtils.readInt(stream);
        }
        this.sendMessage = (TLAbsInputBotInlineMessage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputBotInlineResult#2cbbe15a";
    }
}

