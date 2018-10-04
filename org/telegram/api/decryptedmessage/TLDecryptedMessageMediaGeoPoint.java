
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLAbsDecryptedMessageMedia;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageMediaGeoPoint
extends TLAbsDecryptedMessageMedia {
    public static final int CLASS_ID = 893913689;
    private double latitude;
    private double longtitude;

    @Override
    public int getClassId() {
        return 893913689;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return this.longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeDouble(this.latitude, stream);
        StreamingUtils.writeDouble(this.longtitude, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.latitude = StreamingUtils.readDouble(stream);
        this.longtitude = StreamingUtils.readDouble(stream);
    }

    @Override
    public String toString() {
        return "decryptedMessageMediaGeoPoint#35480a59";
    }
}

