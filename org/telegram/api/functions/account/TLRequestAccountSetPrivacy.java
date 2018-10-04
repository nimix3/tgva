
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.account.TLAccountPrivacyRules;
import org.telegram.api.input.privacy.inputprivacykey.TLAbsInputPrivacyKey;
import org.telegram.api.input.privacy.inputprivacyrule.TLAbsInputPrivacyRule;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestAccountSetPrivacy
extends TLMethod<TLAccountPrivacyRules> {
    public static final int CLASS_ID = -906486552;
    private TLAbsInputPrivacyKey key;
    private TLVector<TLAbsInputPrivacyRule> rules;

    @Override
    public int getClassId() {
        return -906486552;
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

    public TLVector<TLAbsInputPrivacyRule> getRules() {
        return this.rules;
    }

    public void setRules(TLVector<TLAbsInputPrivacyRule> rules) {
        this.rules = rules;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.key, stream);
        StreamingUtils.writeTLVector(this.rules, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.key = (TLAbsInputPrivacyKey)StreamingUtils.readTLObject(stream, context);
        this.rules = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "account.setPrivacy#c9f81ce8";
    }
}

