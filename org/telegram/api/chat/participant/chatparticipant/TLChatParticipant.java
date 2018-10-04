
package org.telegram.api.chat.participant.chatparticipant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.participant.chatparticipant.TLAbsChatParticipant;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatParticipant
extends TLAbsChatParticipant {
    public static final int CLASS_ID = -925415106;
    private int inviterId;
    private int date;

    @Override
    public int getClassId() {
        return -925415106;
    }

    public int getInviterId() {
        return this.inviterId;
    }

    public void setInviterId(int value) {
        this.inviterId = value;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int value) {
        this.date = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeInt(this.inviterId, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.inviterId = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "chatParticipant#c8d7493e";
    }
}

