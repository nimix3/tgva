
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.TLInviteText;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetInviteText
extends TLMethod<TLInviteText> {
    public static final int CLASS_ID = -1532407418;
    private String langCode;

    @Override
    public int getClassId() {
        return -1532407418;
    }

    @Override
    public TLInviteText deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLInviteText) {
            return (TLInviteText)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.help.TLInviteText, got: " + res.getClass().getCanonicalName());
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String value) {
        this.langCode = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.langCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.langCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "help.getInviteText#a4a95186";
    }
}

