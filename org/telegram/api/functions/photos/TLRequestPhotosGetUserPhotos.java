
package org.telegram.api.functions.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.photos.TLAbsPhotos;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestPhotosGetUserPhotos
extends TLMethod<TLAbsPhotos> {
    public static final int CLASS_ID = -1848823128;
    private TLAbsInputUser userId;
    private int offset;
    private int maxId;
    private int limit;

    @Override
    public int getClassId() {
        return -1848823128;
    }

    @Override
    public TLAbsPhotos deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsPhotos) {
            return (TLAbsPhotos)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLAbsPhotos.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputUser getUserId() {
        return this.userId;
    }

    public void setUserId(TLAbsInputUser value) {
        this.userId = value;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public void setMaxId(int value) {
        this.maxId = value;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int value) {
        this.limit = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeInt(this.offset, stream);
        StreamingUtils.writeInt(this.maxId, stream);
        StreamingUtils.writeInt(this.limit, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.offset = StreamingUtils.readInt(stream);
        this.maxId = StreamingUtils.readInt(stream);
        this.limit = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "photos.getUserPhotos#91cd32a8";
    }
}

