
package org.telegram.api.chat.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLChannelForbidden
extends TLAbsChat {
    public static final int CLASS_ID = 763724588;
    private long accessHash;
    private String title;

    @Override
    public int getClassId() {
        return 763724588;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeTLString(this.title, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.title = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "channel.TLChannelForbidden#2d85832c";
    }
}

