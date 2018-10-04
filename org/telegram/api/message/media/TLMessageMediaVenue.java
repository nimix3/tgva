
package org.telegram.api.message.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageMediaVenue
extends TLAbsMessageMedia {
    public static final int CLASS_ID = 2031269663;
    private TLAbsGeoPoint geo;
    private String title;
    private String address;
    private String provider;
    private String venue_id;

    @Override
    public int getClassId() {
        return 2031269663;
    }

    public TLAbsGeoPoint getGeo() {
        return this.geo;
    }

    public void setGeo(TLAbsGeoPoint geo) {
        this.geo = geo;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getVenue_id() {
        return this.venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.geo, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.address, stream);
        StreamingUtils.writeTLString(this.provider, stream);
        StreamingUtils.writeTLString(this.venue_id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.geo = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
        this.title = StreamingUtils.readTLString(stream);
        this.address = StreamingUtils.readTLString(stream);
        this.provider = StreamingUtils.readTLString(stream);
        this.venue_id = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messageMediaVenue#7912b71f";
    }
}

