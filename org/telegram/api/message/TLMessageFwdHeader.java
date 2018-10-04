
package org.telegram.api.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLMessageFwdHeader
extends TLObject {
    public static final int CLASS_ID = -947462709;
    private static final int FLAG_FROM_ID = 1;
    private static final int FLAG_CHANNEL_ID = 2;
    private static final int FLAG_CHANNEL_POST = 4;
    private int flags;
    private int fromId;
    private int date;
    private int channelId;
    private int channelPost;

    @Override
    public int getClassId() {
        return -947462709;
    }

    public int getFromId() {
        return this.fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getChannelPost() {
        return this.channelPost;
    }

    public void setChannelPost(int channelPost) {
        this.channelPost = channelPost;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeInt(this.fromId, stream);
        }
        StreamingUtils.writeInt(this.date, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeInt(this.channelId, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeInt(this.channelPost, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.fromId = StreamingUtils.readInt(stream);
        }
        this.date = StreamingUtils.readInt(stream);
        if ((this.flags & 2) != 0) {
            this.channelId = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 4) != 0) {
            this.channelPost = StreamingUtils.readInt(stream);
        }
    }

    @Override
    public String toString() {
        return "message.messageFwdHeader#c786ddcb";
    }
}

