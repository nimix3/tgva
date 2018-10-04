
package org.telegram.api.messages.dhconfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.messages.dhconfig.TLAbsDhConfig;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;

public class TLDhConfigNotModified
extends TLAbsDhConfig {
    public static final int CLASS_ID = -1058912715;

    @Override
    public int getClassId() {
        return -1058912715;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(this.random, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.random = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "messages.dhConfigNotModified#c0e24635";
    }
}

