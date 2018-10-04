
package org.telegram.api.bot.inlinemessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.inlinemessage.TLAbsBotInlineMessage;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLBotInlineMessageMediaVenue
extends TLAbsBotInlineMessage {
    public static final int CLASS_ID = 982505656;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_REPLY_MARKUP = 4;
    private int flags;
    private TLAbsGeoPoint geoPoint;
    private String title;
    private String address;
    private String provider;
    private String venueId;
    private TLAbsReplyMarkup replyMarkup;

    public int getFlags() {
        return this.flags;
    }

    public TLAbsGeoPoint getGeoPoint() {
        return this.geoPoint;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAddress() {
        return this.address;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getVenueId() {
        return this.venueId;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    @Override
    public int getClassId() {
        return 982505656;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLObject(this.geoPoint, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.address, stream);
        StreamingUtils.writeTLString(this.provider, stream);
        StreamingUtils.writeTLString(this.venueId, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.geoPoint = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
        this.title = StreamingUtils.readTLString(stream);
        this.address = StreamingUtils.readTLString(stream);
        this.provider = StreamingUtils.readTLString(stream);
        this.venueId = StreamingUtils.readTLString(stream);
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "botInlineMessageMediaVenue#4366232e";
    }
}

