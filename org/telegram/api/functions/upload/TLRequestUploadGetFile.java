
package org.telegram.api.functions.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.api.upload.TLFile;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUploadGetFile
extends TLMethod<TLFile> {
    public static final int CLASS_ID = -475607115;
    private TLAbsInputFileLocation location;
    private int offset;
    private int limit;

    @Override
    public int getClassId() {
        return -475607115;
    }

    @Override
    public TLFile deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLFile) {
            return (TLFile)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.upload.TLFile, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputFileLocation getLocation() {
        return this.location;
    }

    public void setLocation(TLAbsInputFileLocation value) {
        this.location = value;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.location, stream);
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.location = (TLAbsInputFileLocation)StreamingUtils.readTLObject(stream, context);
        this.offset = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "upload.getFile#e3a6cfb5";
    }
}

