
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsExportInvite
extends TLMethod<TLAbsChatInvite> {
    public static final int CLASS_ID = -950663035;
    private TLAbsInputChannel channel;

    @Override
    public int getClassId() {
        return -950663035;
    }

    @Override
    public TLAbsChatInvite deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsChatInvite) {
            return (TLAbsChatInvite)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsChatInvite.class.getName() + ", got: " + res.getClass().getName());
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
        return "functions.channels.TLRequestChannelsExportInvite#c7560885";
    }
}

