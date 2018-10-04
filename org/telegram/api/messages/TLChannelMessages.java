
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.TLMessageGroup;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLChannelMessages
extends TLAbsMessages {
    public static final int CLASS_ID = -1139861572;
    private static final int FLAG_COLLAPSED = 1;
    private int flags;
    private int pts;
    private int count;
    private TLVector<TLMessageGroup> collapsed;

    @Override
    public int getClassId() {
        return -1139861572;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TLVector<TLMessageGroup> getCollapsed() {
        return this.collapsed;
    }

    public void setCollapsed(TLVector<TLMessageGroup> collapsed) {
        this.collapsed = collapsed;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLVector(this.collapsed, stream);
        }
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.pts = StreamingUtils.readInt(stream);
        this.count = StreamingUtils.readInt(stream);
        this.messages = StreamingUtils.readTLVector(stream, context);
        if ((this.flags & 1) != 0) {
            this.collapsed = StreamingUtils.readTLVector(stream, context);
        }
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.TLChannelMessages#bc0f17bc";
    }
}

