
package org.telegram.api.input.peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputPeerChat
extends TLAbsInputPeer {
    public static final int CLASS_ID = 396093539;
    private int chatId;

    @Override
    public int getClassId() {
        return 396093539;
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.chatId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.chatId = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "inputPeerChat#179be863";
    }
}

