
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLAccountAuthorizations
extends TLObject {
    public static final int CLASS_ID = 307276766;
    private TLVector<TLAuthorization> authorizations;

    @Override
    public int getClassId() {
        return 307276766;
    }

    public TLVector<TLAuthorization> getAuthorizations() {
        return this.authorizations;
    }

    public void setAuthorizations(TLVector<TLAuthorization> authorizations) {
        this.authorizations = authorizations;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.authorizations, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.authorizations = (TLVector)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "account.authorizations#1250abde";
    }
}

