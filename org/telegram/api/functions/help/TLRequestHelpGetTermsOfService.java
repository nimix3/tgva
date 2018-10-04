
package org.telegram.api.functions.help;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.help.TLTermsOfService;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestHelpGetTermsOfService
extends TLMethod<TLTermsOfService> {
    public static final int CLASS_ID = 936873859;
    private String languageCode;

    @Override
    public int getClassId() {
        return 936873859;
    }

    @Override
    public TLTermsOfService deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLTermsOfService) {
            return (TLTermsOfService)res;
        }
        throw new IOException("Incorrect response type. Expected " + TLTermsOfService.class.getName() + ", got: " + res.getClass().getCanonicalName());
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.languageCode, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.languageCode = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "help.getTermsOfService#37d78f83";
    }
}

