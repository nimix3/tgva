
package org.telegram.api.file.location;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLFileLocationUnavailable
extends TLAbsFileLocation {
    public static final int CLASS_ID = 2086234950;
    private long volumeId;
    private int localId;
    private long secret;

    @Override
    public int getClassId() {
        return 2086234950;
    }

    public long getVolumeId() {
        return this.volumeId;
    }

    public void setVolumeId(long volumeId) {
        this.volumeId = volumeId;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public long getSecret() {
        return this.secret;
    }

    public void setSecret(long secret) {
        this.secret = secret;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.volumeId, stream);
        StreamingUtils.writeInt(this.localId, stream);
        StreamingUtils.writeLong(this.secret, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.volumeId = StreamingUtils.readLong(stream);
        this.localId = StreamingUtils.readInt(stream);
        this.secret = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "fileLocationUnavailable#7c596b46";
    }
}

