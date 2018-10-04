
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLMessagesBotCallbackAnswer
extends TLObject {
    public static final int CLASS_ID = 308605382;
    private static final int FLAG_MESSAGE = 0;
    private static final int FLAG_ALERT = 1;
    private int flags;
    private String message;

    @Override
    public int getClassId() {
        return 308605382;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean hasAlert() {
        return (this.flags & 1) != 0;
    }

    public boolean hasMessage() {
        return (this.flags & 0) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        if ((this.flags & 0) != 0) {
            StreamingUtils.writeTLString(this.message, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        if ((this.flags & 0) != 0) {
            this.message = StreamingUtils.readTLString(stream);
        }
    }

    @Override
    public String toString() {
        return "messages.botCallbackAnswer#1264f1c6";
    }
}

