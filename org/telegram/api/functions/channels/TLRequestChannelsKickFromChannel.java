
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsKickFromChannel
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -1502421484;
    private TLAbsInputChannel channel;
    private TLAbsInputUser userId;
    private boolean kicked;

    @Override
    public int getClassId() {
        return -1502421484;
    }

    @Override
    public TLAbsUpdates deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdates) {
            return (TLAbsUpdates)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getName() + ", got: " + res.getClass().getName());
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

    public boolean isKicked() {
        return this.kicked;
    }

    public void setKicked(boolean kicked) {
        this.kicked = kicked;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeTLBool(this.kicked, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.kicked = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsKickFromChannel#a672de14";
    }
}

