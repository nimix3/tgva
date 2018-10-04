
package org.telegram.api.functions.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUploadSaveBigFilePart
extends TLMethod<TLBool> {
    public static final int CLASS_ID = -562337987;
    private long fileId;
    private int filePart;
    private int fileTotalParts;
    private TLBytes bytes;

    @Override
    public int getClassId() {
        return -562337987;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    public long getFileId() {
        return this.fileId;
    }

    public void setFileId(long value) {
        this.fileId = value;
    }

    public int getFilePart() {
        return this.filePart;
    }

    public void setFilePart(int value) {
        this.filePart = value;
    }

    public int getFileTotalParts() {
        return this.fileTotalParts;
    }

    public void setFileTotalParts(int value) {
        this.fileTotalParts = value;
    }

    public TLBytes getBytes() {
        return this.bytes;
    }

    public void setBytes(TLBytes value) {
        this.bytes = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.fileId, stream);
        StreamingUtils.writeInt(this.filePart, stream);
        StreamingUtils.writeInt(this.fileTotalParts, stream);
        StreamingUtils.writeTLBytes(this.bytes, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.fileId = StreamingUtils.readLong(stream);
        this.filePart = StreamingUtils.readInt(stream);
        this.fileTotalParts = StreamingUtils.readInt(stream);
        this.bytes = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "upload.saveBigFilePart#de7b673d";
    }
}

