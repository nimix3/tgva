
package org.telegram.api.updates.channel.differences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.updates.channel.differences.TLAbsUpdatesChannelDifferences;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdatesChannelDifferences
extends TLAbsUpdatesChannelDifferences {
    public static final int CLASS_ID = 543450958;
    private static final int FLAG_FINAL = 1;
    private static final int FLAG_TIMEOUT = 2;
    private TLVector<TLAbsMessage> newMessages;
    private TLVector<TLAbsUpdate> otherUpdates;
    private TLVector<TLAbsChat> chats;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return 543450958;
    }

    public TLVector<TLAbsMessage> getNewMessages() {
        return this.newMessages;
    }

    public void setNewMessages(TLVector<TLAbsMessage> newMessages) {
        this.newMessages = newMessages;
    }

    public TLVector<TLAbsUpdate> getOtherUpdates() {
        return this.otherUpdates;
    }

    public void setOtherUpdates(TLVector<TLAbsUpdate> otherUpdates) {
        this.otherUpdates = otherUpdates;
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
        StreamingUtils.writeTLVector(this.newMessages, stream);
        StreamingUtils.writeTLVector(this.otherUpdates, stream);
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
        this.newMessages = StreamingUtils.readTLVector(stream, context);
        this.otherUpdates = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "updates.TLUpdatesChannelDifferences#2064674e";
    }
}

