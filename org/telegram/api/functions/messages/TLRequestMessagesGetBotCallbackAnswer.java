
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.messages.TLMessagesBotCallbackAnswer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetBotCallbackAnswer
extends TLMethod<TLMessagesBotCallbackAnswer> {
    public static final int CLASS_ID = 319564933;
    private TLAbsInputPeer peer;
    private int msgId;
    private TLBytes data;

    @Override
    public int getClassId() {
        return 319564933;
    }

    @Override
    public TLMessagesBotCallbackAnswer deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLMessagesBotCallbackAnswer) {
            return (TLMessagesBotCallbackAnswer)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLMessagesBotCallbackAnswer.class.getName() + ", got: " + res.getClass().getName());
    }

    public TLAbsInputPeer getPeer() {
        return this.peer;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public TLBytes getData() {
        return this.data;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.msgId, stream);
        StreamingUtils.writeTLBytes(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        this.msgId = StreamingUtils.readInt(stream);
        this.data = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "messages.getBotCallbackAnswer#a6e94f04";
    }
}

