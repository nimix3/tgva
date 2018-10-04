
package org.telegram.api.channel.participants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.participants.TLAbsChannelParticipant;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChannelParticipantCreator
extends TLAbsChannelParticipant {
    public static final int CLASS_ID = -471670279;
    private int userId;

    @Override
    public int getClassId() {
        return -471670279;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
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
        return "channel.participants.TLChannelParticipantCreator#e3e2e1f9";
    }
}

