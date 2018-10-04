
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsReportSpam
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -32999408;
    private TLAbsInputChannel channel;
    private TLAbsInputUser userId;
    private TLIntVector id;

    @Override
    public int getClassId() {
        return -32999408;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getName() + ", got: " + res.getClass().getName());
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

    public TLIntVector getId() {
        return this.id;
    }

    public void setId(TLIntVector id) {
        this.id = id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeTLVector(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "functions.channels.TLRequestChannelsReportSpam#fe087810";
    }
}

