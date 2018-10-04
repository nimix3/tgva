
package org.telegram.api.functions.users;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestUsersGetUsers
extends TLMethod<TLVector<TLAbsUser>> {
    public static final int CLASS_ID = 227648840;
    protected TLVector<TLAbsInputUser> id;

    @Override
    public int getClassId() {
        return 227648840;
    }

    @Override
    public TLVector<TLAbsUser> deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLVector(stream, context);
    }

    public TLVector<TLAbsInputUser> getId() {
        return this.id;
    }

    public void setId(TLVector<TLAbsInputUser> value) {
        this.id = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.id, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "users.getUsers#d91a548";
    }
}

