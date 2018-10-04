
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.privacy.privacykey.TLAbsPrivacyKey;
import org.telegram.api.privacy.privacyrule.TLAbsPrivacyRule;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLUpdatePrivacy
extends TLAbsUpdate {
    public static final int CLASS_ID = -298113238;
    private TLAbsPrivacyKey key;
    private TLVector<TLAbsPrivacyRule> rules;

    @Override
    public int getClassId() {
        return -298113238;
    }

    public TLAbsPrivacyKey getKey() {
        return this.key;
    }

    public void setKey(TLAbsPrivacyKey key) {
        this.key = key;
    }

    public TLVector<TLAbsPrivacyRule> getRules() {
        return this.rules;
    }

    public void setRules(TLVector<TLAbsPrivacyRule> rules) {
        this.rules = rules;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.key, stream);
        StreamingUtils.writeTLVector(this.rules, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.key = (TLAbsPrivacyKey)StreamingUtils.readTLObject(stream, context);
        this.rules = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "updatePrivacy#ee3b272a";
    }
}

