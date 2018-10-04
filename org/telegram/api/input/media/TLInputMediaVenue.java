
package org.telegram.api.input.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputMediaVenue
extends TLObject {
    public static final int CLASS_ID = 673687578;
    private TLAbsInputGeoPoint inputGeoPoint;
    private String title;
    private String address;
    private String provider;
    private String venueId;

    @Override
    public int getClassId() {
        return 673687578;
    }

    public TLAbsInputGeoPoint getInputGeoPoint() {
        return this.inputGeoPoint;
    }

    public void setInputGeoPoint(TLAbsInputGeoPoint inputGeoPoint) {
        this.inputGeoPoint = inputGeoPoint;
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

    public String getVenueId() {
        return this.venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeTLObject(this.inputGeoPoint, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.address, stream);
        StreamingUtils.writeTLString(this.provider, stream);
        StreamingUtils.writeTLString(this.venueId, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.inputGeoPoint = (TLAbsInputGeoPoint)StreamingUtils.readTLObject(stream, context);
        this.title = StreamingUtils.readTLString(stream);
        this.address = StreamingUtils.readTLString(stream);
        this.provider = StreamingUtils.readTLString(stream);
        this.venueId = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "input.mediaVenue#2827a81a";
    }
}

