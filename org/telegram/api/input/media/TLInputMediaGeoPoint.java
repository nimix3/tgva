
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputMediaGeoPoint
extends TLAbsInputMedia {
    public static final int CLASS_ID = -104578748;
    protected TLAbsInputGeoPoint geoPoint;

    @Override
    public int getClassId() {
        return -104578748;
    }

    public TLAbsInputGeoPoint getGeoPoint() {
        return this.geoPoint;
    }

    public void setGeoPoint(TLAbsInputGeoPoint value) {
        this.geoPoint = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.geoPoint, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.geoPoint = (TLAbsInputGeoPoint)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "inputMediaGeoPoint#f9c44144";
    }
}

