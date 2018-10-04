
package org.telegram.api.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.TLAbsAppUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLAppUpdate
extends TLAbsAppUpdate {
    public static final int CLASS_ID = -1987579119;

    @Override
    public int getClassId() {
        return -1987579119;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLBool(this.critical, stream);
        StreamingUtils.writeTLString(this.url, stream);
        StreamingUtils.writeTLString(this.text, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.critical = StreamingUtils.readTLBool(stream);
        this.url = StreamingUtils.readTLString(stream);
        this.text = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "help.appUpdate#8987f311";
    }
}

