
package org.telegram.api.chat.participant.chatparticipants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.participant.chatparticipant.TLAbsChatParticipant;
import org.telegram.api.chat.participant.chatparticipants.TLAbsChatParticipants;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLChatParticipants
extends TLAbsChatParticipants {
    public static final int CLASS_ID = 1061556205;
    private TLVector<TLAbsChatParticipant> participants;
    private int version;

    @Override
    public int getClassId() {
        return 1061556205;
    }

    public TLVector<TLAbsChatParticipant> getParticipants() {
        return this.participants;
    }

    public void setParticipants(TLVector<TLAbsChatParticipant> participants) {
        this.participants = participants;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
        StreamingUtils.writeTLVector(this.participants, stream);
        StreamingUtils.writeInt(this.version, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
        this.participants = StreamingUtils.readTLVector(stream, context);
        this.version = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "chatParticipants#3f460fed";
    }
}

