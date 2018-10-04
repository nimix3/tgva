
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dhconfig.TLAbsDhConfig;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesGetDhConfig
extends TLMethod<TLAbsDhConfig> {
    public static final int CLASS_ID = 651135312;
    private int version;
    private int randomLength;

    @Override
    public int getClassId() {
        return 651135312;
    }

    @Override
    public TLAbsDhConfig deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsDhConfig) {
            return (TLAbsDhConfig)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.messages.dhconfig.TLAbsDhConfig, got: " + res.getClass().getCanonicalName());
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int value) {
        this.version = value;
    }

    public int getRandomLength() {
        return this.randomLength;
    }

    public void setRandomLength(int value) {
        this.randomLength = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.version, stream);
        StreamingUtils.writeInt(this.randomLength, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.version = StreamingUtils.readInt(stream);
        this.randomLength = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.getDhConfig#26cf8950";
    }
}

