
package org.telegram.api.message.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageMediaGeo
extends TLAbsMessageMedia {
    public static final int CLASS_ID = 1457575028;
    private TLAbsGeoPoint geo;

    @Override
    public int getClassId() {
        return 1457575028;
    }

    public TLAbsGeoPoint getGeo() {
        return this.geo;
    }

    public void setGeo(TLAbsGeoPoint geo) {
        this.geo = geo;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.geo, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.geo = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messageMediaGeo#56e0d474";
    }
}

