
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.inlineresult.TLAbsBotInlineResult;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetInlineBotResults
extends TLMethod<TLAbsBotInlineResult> {
    public static final int CLASS_ID = 1364105629;
    private static final int FLAG_GEO_POINT = 1;
    private int flags;
    private TLAbsInputUser bot;
    private TLAbsInputPeer peer;
    private TLAbsInputGeoPoint geoPoint;
    private String query;
    private String offset;

    @Override
    public int getClassId() {
        return 1364105629;
    }

    public TLAbsInputUser getBot() {
        return this.bot;
    }

    public String getQuery() {
        return this.query;
    }

    public String getOffset() {
        return this.offset;
    }

    @Override
    public TLAbsBotInlineResult deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsBotInlineResult) {
            return (TLAbsBotInlineResult)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsBotInlineResult.class.getCanonicalName() + ", got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.bot, stream);
        StreamingUtils.writeTLObject(this.peer, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.geoPoint, stream);
        }
        StreamingUtils.writeTLString(this.query, stream);
        StreamingUtils.writeTLString(this.offset, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.bot = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.peer = (TLAbsInputPeer)StreamingUtils.readTLObject(stream, context);
        if ((this.flags & 1) != 0) {
            this.geoPoint = (TLAbsInputGeoPoint)StreamingUtils.readTLObject(stream, context);
        }
        this.query = StreamingUtils.readTLString(stream);
        this.offset = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messages.getInlineBotResults#514e999d";
    }
}

