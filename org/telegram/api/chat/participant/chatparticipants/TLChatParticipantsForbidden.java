
package org.telegram.api.chat.participant.chatparticipants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.participant.chatparticipant.TLAbsChatParticipant;
import org.telegram.api.chat.participant.chatparticipants.TLAbsChatParticipants;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatParticipantsForbidden
extends TLAbsChatParticipants {
    public static final int CLASS_ID = -57668565;
    private static final int FLAG_SELF = 1;
    private int flags;
    private TLAbsChatParticipant selfParticipant;

    @Override
    public int getClassId() {
        return -57668565;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public TLAbsChatParticipant getSelfParticipant() {
        return this.selfParticipant;
    }

    public void setSelfParticipant(TLAbsChatParticipant selfParticipant) {
        this.selfParticipant = selfParticipant;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.chatId, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.selfParticipant, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.chatId = StreamingUtils.readInt(stream);
        if ((this.flags & 1) != 0) {
            this.selfParticipant = (TLAbsChatParticipant)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "chatParticipantsForbidden#fc900c2b";
    }
}

