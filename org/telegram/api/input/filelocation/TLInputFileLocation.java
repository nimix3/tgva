
package org.telegram.api.input.filelocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.file.location.TLFileLocation;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputFileLocation
extends TLAbsInputFileLocation {
    public static final int CLASS_ID = 342061462;
    private long volumeId;
    private int localId;
    private long secret;

    public TLInputFileLocation() {
    }

    public TLInputFileLocation(TLFileLocation fileLocation) {
        this.volumeId = fileLocation.getVolumeId();
        this.localId = fileLocation.getLocalId();
        this.secret = fileLocation.getSecret();
    }

    @Override
    public int getClassId() {
        return 342061462;
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
        return "inputFileLocation#14637196";
    }
}

