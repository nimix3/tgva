
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.participant.chatparticipants.TLAbsChatParticipants;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateChatParticipants
extends TLAbsUpdate {
    public static final int CLASS_ID = 125178264;
    private TLAbsChatParticipants participants;

    @Override
    public int getClassId() {
        return 125178264;
    }

    public TLAbsChatParticipants getParticipants() {
        return this.participants;
    }

    public void setParticipants(TLAbsChatParticipants participants) {
        this.participants = participants;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.participants, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.participants = (TLAbsChatParticipants)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateChatParticipants#7761198";
    }
}

