
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.messages.TLMessagesChatFull;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsGetFullChannel
extends TLMethod<TLMessagesChatFull> {
    public static final int CLASS_ID = 141781513;
    private TLAbsInputChannel channel;

    @Override
    public int getClassId() {
        return 141781513;
    }

    @Override
    public TLMessagesChatFull deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLMessagesChatFull) {
            return (TLMessagesChatFull)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLMessagesChatFull.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLAbsInputChannel getChannel() {
        return this.channel;
    }

    public void setChannel(TLAbsInputChannel channel) {
        this.channel = channel;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsGetFullChannel#8736a09";
    }
}

