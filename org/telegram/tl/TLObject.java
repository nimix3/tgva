
package org.telegram.tl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public abstract class TLObject
implements Serializable {
    public abstract int getClassId();

    public abstract String toString();

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.serialize(stream);
        return stream.toByteArray();
    }

    public void serialize(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.getClassId(), stream);
        this.serializeBody(stream);
    }

    public void deserialize(InputStream stream, TLContext context) throws IOException {
        int classId = StreamingUtils.readInt(stream);
        if (classId != this.getClassId()) {
            throw new DeserializeException("Wrong class id. Founded:" + Integer.toHexString(classId) + ", expected: " + Integer.toHexString(this.getClassId()));
        }
        this.deserializeBody(stream, context);
    }

    public void serializeBody(OutputStream stream) throws IOException {
    }

    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
    }
}

