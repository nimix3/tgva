
package org.telegram.api.disablefeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLDisabledFeature
extends TLObject {
    public static final int CLASS_ID = -1369215196;
    private String feature;
    private String description;

    @Override
    public int getClassId() {
        return -1369215196;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.feature, stream);
        StreamingUtils.writeTLString(this.description, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.feature = StreamingUtils.readTLString(stream);
        this.description = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "disableFeature#ae636f24";
    }
}

