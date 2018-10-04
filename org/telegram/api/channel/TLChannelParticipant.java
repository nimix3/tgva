
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

public class TLChannelParticipant
extends TLObject {
    public static final int CLASS_ID = -791039645;
    private TLAbsChannelParticipant participant;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return -791039645;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    public TLAbsChannelParticipant getParticipant() {
        return this.participant;
    }

    public void setParticipant(TLAbsChannelParticipant participant) {
        this.participant = participant;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.participant, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.participant = (TLAbsChannelParticipant)StreamingUtils.readTLObject(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "channel.TLChannelParticipant#d0d9b163";
    }
}

