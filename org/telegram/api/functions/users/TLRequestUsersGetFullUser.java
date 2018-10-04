
package org.telegram.api.functions.users;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.user.TLUserFull;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestUsersGetFullUser
extends TLMethod<TLUserFull> {
    public static final int CLASS_ID = -902781519;
    private TLAbsInputUser id;

    @Override
    public int getClassId() {
        return -902781519;
    }

    @Override
    public TLUserFull deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLUserFull) {
            return (TLUserFull)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.user.TLUserFull, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputUser getId() {
        return this.id;
    }

    public void setId(TLAbsInputUser value) {
        this.id = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "users.getFullUser#ca30a5b1";
    }
}

