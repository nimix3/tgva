
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsImportCard
extends TLMethod<TLAbsUser> {
    public static final int CLASS_ID = 1340184318;
    private TLIntVector exportCard;

    @Override
    public int getClassId() {
        return 1340184318;
    }

    @Override
    public TLAbsUser deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUser) {
            return (TLAbsUser)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.user.TLAbsUser, got: " + res.getClass().getCanonicalName());
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        super.serializeBody(stream);
        StreamingUtils.writeTLVector(this.exportCard, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        super.deserializeBody(stream, context);
        this.exportCard = StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.importCard#4fe196fe";
    }
}

