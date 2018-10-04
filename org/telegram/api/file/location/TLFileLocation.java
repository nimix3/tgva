
package org.telegram.api.file.location;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLFileLocation
extends TLAbsFileLocation {
    public static final int CLASS_ID = 1406570614;
    private int dcId;
    private long volumeId;
    private int localId;
    private long secret;

    @Override
    public int getClassId() {
        return 1406570614;
    }

    public int getDcId() {
        return this.dcId;
    }

    public void setDcId(int dcId) {
        this.dcId = dcId;
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
        StreamingUtils.writeInt(this.dcId, stream);
        StreamingUtils.writeLong(this.volumeId, stream);
        StreamingUtils.writeInt(this.localId, stream);
        StreamingUtils.writeLong(this.secret, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.dcId = StreamingUtils.readInt(stream);
        this.volumeId = StreamingUtils.readLong(stream);
        this.localId = StreamingUtils.readInt(stream);
        this.secret = StreamingUtils.readLong(stream);
    }

    @Override
    public String toString() {
        return "fileLocation#53d69076";
    }
}

