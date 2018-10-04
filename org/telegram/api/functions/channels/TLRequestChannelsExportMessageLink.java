
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.message.TLExportedMessageLink;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsExportMessageLink
extends TLMethod<TLExportedMessageLink> {
    public static final int CLASS_ID = -934882771;
    private TLAbsInputChannel channel;
    private int id;

    @Override
    public int getClassId() {
        return -934882771;
    }

    @Override
    public TLExportedMessageLink deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLExportedMessageLink) {
            return (TLExportedMessageLink)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLExportedMessageLink.class.getName() + ", got: " + res.getClass().getName());
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
        StreamingUtils.writeInt(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.channel = (TLAbsInputChannel)StreamingUtils.readTLObject(stream, context);
        this.id = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "channels.exportMessageLink#c846d22d";
    }
}

