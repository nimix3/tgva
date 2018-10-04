
package org.telegram.api.input.reportspamreason;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLReportSpamReasonOther
extends TLObject {
    public static final int CLASS_ID = -512463606;
    private String text;

    @Override
    public int getClassId() {
        return -512463606;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.text, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.text = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "reportSpamReasonPornography#2e59d922";
    }
}

