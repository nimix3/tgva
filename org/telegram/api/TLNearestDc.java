
package org.telegram.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLNearestDc
extends TLObject {
    public static final int CLASS_ID = -1910892683;
    private String country;
    private int thisDc;
    private int nearestDc;

    @Override
    public int getClassId() {
        return -1910892683;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public int getThisDc() {
        return this.thisDc;
    }

    public void setThisDc(int value) {
        this.thisDc = value;
    }

    public int getNearestDc() {
        return this.nearestDc;
    }

    public void setNearestDc(int value) {
        this.nearestDc = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.country, stream);
        StreamingUtils.writeInt(this.thisDc, stream);
        StreamingUtils.writeInt(this.nearestDc, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.country = StreamingUtils.readTLString(stream);
        this.thisDc = StreamingUtils.readInt(stream);
        this.nearestDc = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "nearestDc#8e1a1775";
    }
}

