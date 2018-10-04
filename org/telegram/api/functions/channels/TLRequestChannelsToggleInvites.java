
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsToggleInvites
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = 1231065863;
    private static final int FLAG_BROADCAST = 1;
    private static final int FLAG_MEGAGROUP = 2;
    private TLAbsInputChannel channel;
    private boolean enabled;

    @Override
    public int getClassId() {
        return 1231065863;
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

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.channel, stream);
        StreamingUtils.writeTLBool(this.enabled, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.enabled = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "channels.toggleInvites#49609307";
    }
}

