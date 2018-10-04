
package org.telegram.api.updates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdates
extends TLAbsUpdates {
    public static final int CLASS_ID = 1957577280;
    private TLVector<TLAbsUpdate> updates;
    private TLVector<TLAbsUser> users;
    private TLVector<TLAbsChat> chats;
    private int date;
    private int seq;

    public TLVector<TLAbsUpdate> getUpdates() {
        return this.updates;
    }

    public void setUpdates(TLVector<TLAbsUpdate> updates) {
        this.updates = updates;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    public TLVector<TLAbsChat> getChats() {
        return this.chats;
    }

    public void setChats(TLVector<TLAbsChat> chats) {
        this.chats = chats;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public int getClassId() {
        return 1957577280;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.updates, stream);
        StreamingUtils.writeTLVector(this.users, stream);
        StreamingUtils.writeTLVector(this.chats, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeInt(this.seq, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.updates = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
        this.chats = StreamingUtils.readTLVector(stream, context);
        this.date = StreamingUtils.readInt(stream);
        this.seq = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updates#74ae4240";
    }
}

