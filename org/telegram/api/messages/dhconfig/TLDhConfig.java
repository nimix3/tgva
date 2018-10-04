
package org.telegram.api.messages.dhconfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dhconfig.TLAbsDhConfig;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDhConfig
extends TLAbsDhConfig {
    public static final int CLASS_ID = 740433629;

    @Override
    public int getClassId() {
        return 740433629;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.g, stream);
        StreamingUtils.writeTLBytes(this.p, stream);
        StreamingUtils.writeInt(this.version, stream);
        StreamingUtils.writeTLBytes(this.random, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.g = StreamingUtils.readInt(stream);
        this.p = StreamingUtils.readTLBytes(stream, context);
        this.version = StreamingUtils.readInt(stream);
        this.random = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "messages.dhConfig#2c221edd";
    }
}

