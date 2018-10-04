
package org.telegram.api.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInviteText
extends TLObject {
    public static final int CLASS_ID = 415997816;
    private String message;

    @Override
    public int getClassId() {
        return 415997816;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.message = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "help.inviteText#18cb9f78";
    }
}

