
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountPrivacyRules;
import org.telegram.api.input.privacy.inputprivacykey.TLAbsInputPrivacyKey;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestAccountGetPrivacy
extends TLMethod<TLAccountPrivacyRules> {
    public static final int CLASS_ID = -623130288;
    private TLAbsInputPrivacyKey key;

    @Override
    public int getClassId() {
        return -623130288;
    }

    @Override
    public TLAccountPrivacyRules deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAccountPrivacyRules) {
            return (TLAccountPrivacyRules)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.account.TLAccountPrivacyRules, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputPrivacyKey getKey() {
        return this.key;
    }

    public void setKey(TLAbsInputPrivacyKey key) {
        this.key = key;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.key, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.key = (TLAbsInputPrivacyKey)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.getPrivacy#dadbc950";
    }
}

