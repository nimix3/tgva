
package org.telegram.api.input.geopoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputGeoPoint
extends TLAbsInputGeoPoint {
    public static final int CLASS_ID = -206066487;
    private double lat;
    private double lon;

    @Override
    public int getClassId() {
        return -206066487;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeDouble(this.lat, stream);
        StreamingUtils.writeDouble(this.lon, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.lat = StreamingUtils.readDouble(stream);
        this.lon = StreamingUtils.readDouble(stream);
    }

    @Override
    public String toString() {
        return "inputGeoPoint#f3b7acc9";
    }
}

