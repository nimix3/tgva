
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.channel.TLChannelParticipant;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsGetParticipant
extends TLMethod<TLChannelParticipant> {
    public static final int CLASS_ID = 1416484774;
    private TLAbsInputChannel channel;
    private TLAbsInputUser userId;

    @Override
    public int getClassId() {
        return 1416484774;
    }

    @Override
    public TLChannelParticipant deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLChannelParticipant) {
            return (TLChannelParticipant)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLChannelParticipant.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLAbsInputChannel getChannel() {
        return this.channel;
    }

    public void setChannel(TLAbsInputChannel channel) {
        this.channel = channel;
    }

    public TLAbsInputUser getUserId() {
        return this.userId;
    }

    public void setUserId(TLAbsInputUser userId) {
        this.userId = userId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeTLObject(this.userId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsGetParticipant#546dd7a6";
    }
}

