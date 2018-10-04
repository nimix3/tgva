
package org.telegram.api.help.changelog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.changelog.TLAbsAppChangelog;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLAppChangelog
extends TLAbsAppChangelog {
    public static final int CLASS_ID = 1181279933;
    private String text;

    @Override
    public int getClassId() {
        return 1181279933;
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
        return "help.AppChangelog#4668e6bd";
    }
}

