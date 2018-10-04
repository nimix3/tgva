
package org.telegram.api.updates.channel.differences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.updates.channel.differences.TLAbsUpdatesChannelDifferences;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdatesChannelDifferencesTooLong
extends TLAbsUpdatesChannelDifferences {
    public static final int CLASS_ID = 1578530374;
    private static final int FLAG_FINAL = 1;
    private static final int FLAG_TIMEOUT = 2;
    private int topMessage;
    private int topImportantMessage;
    private int readInboxMaxId;
    private int unreadCount;
    private int unreadImportantCount;
    private TLVector<TLAbsMessage> messages;
    private TLVector<TLAbsChat> chats;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return 1578530374;
    }

    public int getTopMessage() {
        return this.topMessage;
    }

    public void setTopMessage(int topMessage) {
        this.topMessage = topMessage;
    }

    public int getTopImportantMessage() {
        return this.topImportantMessage;
    }

    public void setTopImportantMessage(int topImportantMessage) {
        this.topImportantMessage = topImportantMessage;
    }

    public int getReadInboxMaxId() {
        return this.readInboxMaxId;
    }

    public void setReadInboxMaxId(int readInboxMaxId) {
        this.readInboxMaxId = readInboxMaxId;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadImportantCount() {
        return this.unreadImportantCount;
    }

    public void setUnreadImportantCount(int unreadImportantCount) {
        this.unreadImportantCount = unreadImportantCount;
    }

    public TLVector<TLAbsMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(TLVector<TLAbsMessage> messages) {
        this.messages = messages;
    }

    public TLVector<TLAbsChat> getChats() {
        return this.chats;
    }

    public void setChats(TLVector<TLAbsChat> chats) {
        this.chats = chats;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.pts, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeInt(this.timeout, stream);
        }
        StreamingUtils.writeInt(this.topMessage, stream);
        StreamingUtils.writeInt(this.topImportantMessage, stream);
        StreamingUtils.writeInt(this.readInboxMaxId, stream);
        StreamingUtils.writeInt(this.unreadCount, stream);
        StreamingUtils.writeInt(this.unreadImportantCount, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.pts = StreamingUtils.readInt(stream);
        if ((this.flags & 2) != 0) {
            this.timeout = StreamingUtils.readInt(stream);
        }
        this.topMessage = StreamingUtils.readInt(stream);
        this.topImportantMessage = StreamingUtils.readInt(stream);
        this.readInboxMaxId = StreamingUtils.readInt(stream);
        this.unreadCount = StreamingUtils.readInt(stream);
        this.unreadImportantCount = StreamingUtils.readInt(stream);
        this.messages = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "updates.TLUpdatesChannelDifferencesTooLong#5e167646";
    }
}

