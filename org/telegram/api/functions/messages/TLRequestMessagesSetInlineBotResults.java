
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.TLInlineBotSwitchPm;
import org.telegram.api.input.bot.inlineresult.TLAbsInputBotInlineResult;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesSetInlineBotResults
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -346119674;
    private static final int FLAG_GALLERY = 1;
    private static final int FLAG_PRIVATE = 2;
    private static final int FLAG_NEXT_OFFSET = 4;
    private static final int FLAG_SWITCH_PM = 8;
    private int flags;
    private long queryId;
    private TLVector<TLAbsInputBotInlineResult> results;
    private int cacheTime;
    private String nextOffset;
    private TLInlineBotSwitchPm switchPm;

    public long getQueryId() {
        return this.queryId;
    }

    public TLVector<TLAbsInputBotInlineResult> getResults() {
        return this.results;
    }

    public int getCacheTime() {
        return this.cacheTime;
    }

    public String getNextOffset() {
        return this.nextOffset;
    }

    public TLInlineBotSwitchPm getSwitchPm() {
        return this.switchPm;
    }

    @Override
    public int getClassId() {
        return -346119674;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLBool.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.queryId, stream);
        StreamingUtils.writeTLVector(this.results, stream);
        StreamingUtils.writeInt(this.cacheTime, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.nextOffset, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLObject(this.switchPm, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.queryId = StreamingUtils.readLong(stream);
        this.results = StreamingUtils.readTLVector(stream, context);
        this.cacheTime = StreamingUtils.readInt(stream);
        if ((this.flags & 4) != 0) {
            this.nextOffset = StreamingUtils.readTLString(stream);
        }
        this.switchPm = (TLInlineBotSwitchPm)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.setInlineBotResults#eb5ea206";
    }
}

