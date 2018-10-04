
package org.telegram.api.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.TLInlineBotSwitchPm;
import org.telegram.api.bot.inlineresult.TLAbsBotInlineResult;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLBotResults
extends TLObject {
    public static final int CLASS_ID = 292597923;
    private static final int FLAG_GALLERY = 1;
    private static final int FLAG_NEXT_OFFSET = 2;
    private static final int FLAG_SWITCH_PM = 4;
    private int flags;
    private long queryId;
    private String nextOffset;
    private TLVector<TLAbsBotInlineResult> results;
    private TLInlineBotSwitchPm switchPm;

    public long getQueryId() {
        return this.queryId;
    }

    public String getNextOffset() {
        return this.nextOffset;
    }

    public TLVector<TLAbsBotInlineResult> getResults() {
        return this.results;
    }

    public int getFlags() {
        return this.flags;
    }

    public TLInlineBotSwitchPm getSwitchPm() {
        return this.switchPm;
    }

    @Override
    public int getClassId() {
        return 292597923;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.queryId, stream);
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.nextOffset, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.switchPm, stream);
        }
        StreamingUtils.writeTLVector(this.results, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.queryId = StreamingUtils.readLong(stream);
        if ((this.flags & 2) != 0) {
            this.nextOffset = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.switchPm = (TLInlineBotSwitchPm)StreamingUtils.readTLObject(stream, context);
        }
        this.results = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "messages.botResults#1170b0a3";
    }
}

