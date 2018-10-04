
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.TLDcOption;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdateDcOptions
extends TLAbsUpdate {
    public static final int CLASS_ID = -1906403213;
    private TLVector<TLDcOption> dcOptions = new TLVector();

    @Override
    public int getClassId() {
        return -1906403213;
    }

    public TLVector getDcOptions() {
        return this.dcOptions;
    }

    public void setDcOptions(TLVector<TLDcOption> dcOptions) {
        this.dcOptions = dcOptions;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.dcOptions, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.dcOptions = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "updateDcOptions#8e5e9873";
    }
}

