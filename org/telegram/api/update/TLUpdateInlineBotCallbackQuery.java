
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.TLInputBotInlineMessageId;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLUpdateInlineBotCallbackQuery
extends TLAbsUpdate {
    public static final int CLASS_ID = 750622127;
    private long queryId;
    private int userId;
    private TLInputBotInlineMessageId msgId;
    private TLBytes data;

    @Override
    public int getClassId() {
        return 750622127;
    }

    public long getQueryId() {
        return this.queryId;
    }

    public int getUserId() {
        return this.userId;
    }

    public TLInputBotInlineMessageId getMsgId() {
        return this.msgId;
    }

    public TLBytes getData() {
        return this.data;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.queryId, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.msgId, stream);
        StreamingUtils.writeTLBytes(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.queryId = StreamingUtils.readLong(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.msgId = (TLInputBotInlineMessageId)StreamingUtils.readTLObject(stream, context);
        this.data = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "updateInlineBotCallbackQuery#2cbd95af";
    }
}

