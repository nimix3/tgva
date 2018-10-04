
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.api.input.bot.TLInputBotInlineMessageId;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateBotInlineSend
extends TLAbsUpdate {
    public static final int CLASS_ID = 239663460;
    private static final int FLAG_GEOPOINT = 1;
    private static final int FLAG_MSG_ID = 2;
    private int flags;
    private int userId;
    private String query;
    private TLAbsGeoPoint geo;
    private String id;
    private TLInputBotInlineMessageId msgId;

    @Override
    public int getClassId() {
        return 239663460;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getQuery() {
        return this.query;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLString(this.query, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.geo, stream);
        }
        StreamingUtils.writeTLString(this.id, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.msgId, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.query = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.geo = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
        }
        this.id = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.msgId = (TLInputBotInlineMessageId)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "updateBotInlineSend#e48f964";
    }
}

