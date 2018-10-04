
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.messages.TLAffectedMessages;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsDeleteMessages
extends TLMethod<TLAffectedMessages> {
    public static final int CLASS_ID = -2067661490;
    private TLAbsInputChannel channel;
    private TLIntVector id;

    @Override
    public int getClassId() {
        return -2067661490;
    }

    @Override
    public TLAffectedMessages deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAffectedMessages) {
            return (TLAffectedMessages)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAffectedMessages.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLIntVector getId() {
        return this.id;
    }

    public void setId(TLIntVector id) {
        this.id = id;
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
        StreamingUtils.writeTLVector(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsDeleteMessages#84c1fd4e";
    }
}

