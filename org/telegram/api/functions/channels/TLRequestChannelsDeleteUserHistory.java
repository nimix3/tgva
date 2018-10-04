
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.messages.TLAffectedHistory;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsDeleteUserHistory
extends TLMethod<TLAffectedHistory> {
    public static final int CLASS_ID = -787622117;
    private TLAbsInputChannel channel;
    private TLAbsInputUser userId;

    @Override
    public int getClassId() {
        return -787622117;
    }

    @Override
    public TLAffectedHistory deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAffectedHistory) {
            return (TLAffectedHistory)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAffectedHistory.class.getName() + ", got: " + res.getClass().getName());
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
        return "functions.channels.TLRequestChannelsDeleteUserHistory#d10dd71b";
    }
}

