
package org.telegram.api.geo.point;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLGeoPoint
extends TLAbsGeoPoint {
    public static final int CLASS_ID = 541710092;
    private double lon;
    private double lat;

    @Override
    public int getClassId() {
        return 541710092;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeDouble(this.lon, stream);
        StreamingUtils.writeDouble(this.lat, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.lon = StreamingUtils.readDouble(stream);
        this.lat = StreamingUtils.readDouble(stream);
    }

    @Override
    public String toString() {
        return "geoPoint#2049d70c";
    }
}

