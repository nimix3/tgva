
package org.telegram.api.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.participants.TLAbsChannelParticipant;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLChannelParticipants
extends TLObject {
    public static final int CLASS_ID = -177282392;
    private int count;
    private TLVector<TLAbsChannelParticipant> participants;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return -177282392;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TLVector<TLAbsChannelParticipant> getParticipants() {
        return this.participants;
    }

    public void setParticipants(TLVector<TLAbsChannelParticipant> participants) {
        this.participants = participants;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.count, stream);
        StreamingUtils.writeTLVector(this.participants, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.count = StreamingUtils.readInt(stream);
        this.participants = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "channel.TLChannelParticipants#f56ee2a8";
    }
}

