
package org.telegram.api.bot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInlineBotSwitchPm
extends TLObject {
    public static final int CLASS_ID = 1008755359;
    private String text;
    private String startParam;

    @Override
    public int getClassId() {
        return 1008755359;
    }

    public String getText() {
        return this.text;
    }

    public String getStartParam() {
        return this.startParam;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
        StreamingUtils.writeTLString(this.startParam, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
        this.startParam = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inlineBotSwitchPM#3c20629f";
    }
}

