
package org.telegram.api.chat.participant.chatparticipant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.participant.chatparticipant.TLAbsChatParticipant;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChatParticipantCreator
extends TLAbsChatParticipant {
    public static final int CLASS_ID = -636267638;

    @Override
    public int getClassId() {
        return -636267638;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "chatParticipantCreator#da13538a";
    }
}

