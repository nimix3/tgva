
package org.telegram.api.channel.participants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.participants.TLAbsChannelParticipant;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChannelParticipantKicked
extends TLAbsChannelParticipant {
    public static final int CLASS_ID = -1933187430;
    private int userId;
    private int kickedBy;
    private int date;

    @Override
    public int getClassId() {
        return -1933187430;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getKickedBy() {
        return this.kickedBy;
    }

    public void setKickedBy(int kickedBy) {
        this.kickedBy = kickedBy;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeInt(this.kickedBy, stream);
        StreamingUtils.writeInt(this.date, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.kickedBy = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "channel.participants.TLChannelParticipantKicked#8cc5e69a";
    }
}

