
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateBotInlineQuery
extends TLAbsUpdate {
    public static final int CLASS_ID = 1417832080;
    private static final int FLAG_GEOPOINT = 1;
    private int flags;
    private long queryId;
    private int userId;
    private String query;
    private TLAbsGeoPoint geo;
    private String offset;

    @Override
    public int getClassId() {
        return 1417832080;
    }

    public long getQueryId() {
        return this.queryId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getQuery() {
        return this.query;
    }

    public String getOffset() {
        return this.offset;
    }

    public int getFlags() {
        return this.flags;
    }

    public TLAbsGeoPoint getGeo() {
        return this.geo;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.queryId, stream);
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLString(this.query, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLObject(this.geo, stream);
        }
        StreamingUtils.writeTLString(this.offset, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.queryId = StreamingUtils.readLong(stream);
        this.userId = StreamingUtils.readInt(stream);
        this.query = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.geo = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
        }
        this.offset = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "updateBotInlineQuery#54826690";
    }
}

