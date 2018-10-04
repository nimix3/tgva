
package org.telegram.api.functions.photos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestPhotosDeletePhotos
extends TLMethod<TLLongVector> {
    public static final int CLASS_ID = -2016444625;
    private TLVector<TLAbsInputPhoto> id;

    @Override
    public int getClassId() {
        return -2016444625;
    }

    @Override
    public TLLongVector deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLLongVector) {
            return (TLLongVector)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.TLLongVector, got: " + res.getClass().getCanonicalName());
    }

    public TLVector<TLAbsInputPhoto> getId() {
        return this.id;
    }

    public void setId(TLVector<TLAbsInputPhoto> id) {
        this.id = id;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "photos.deletePhotos#87cf7f2f";
    }
}

