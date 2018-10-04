
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLMessagesEditData
extends TLObject {
    public static final int CLASS_ID = 649453030;
    private static final int FLAG_CAPTION = 1;
    private int flags;

    @Override
    public int getClassId() {
        return 649453030;
    }

    public boolean isCaptionEdited() {
        return (this.flags & 1) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.messageEditData#26b5dde6";
    }
}

