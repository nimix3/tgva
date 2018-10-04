
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import org.telegram.mtproto.tl.MTMessage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTMessagesContainer
extends TLObject {
    public static final int CLASS_ID = 1945237724;
    private TreeSet<MTMessage> messages = new TreeSet(new Comparator<MTMessage>(){

        @Override
        public int compare(MTMessage mtMessage, MTMessage mtMessage2) {
            return (int)Math.signum(mtMessage.getMessageId() - mtMessage2.getMessageId());
        }
    });

    public MTMessagesContainer(MTMessage[] messages) {
        Collections.addAll(this.messages, messages);
    }

    public MTMessagesContainer() {
    }

    public TreeSet<MTMessage> getMessages() {
        return this.messages;
    }

    @Override
    public int getClassId() {
        return 1945237724;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.messages.size(), stream);
        for (MTMessage message : this.messages) {
            message.serializeBody(stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        int size = StreamingUtils.readInt(stream);
        this.messages.clear();
        for (int i = 0; i < size; ++i) {
            MTMessage message = new MTMessage();
            message.deserializeBody(stream, context);
            this.messages.add(message);
        }
    }

    @Override
    public String toString() {
        return "msg_container#73f1f8dc";
    }

}

