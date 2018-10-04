
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesCreateChat
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = 164303470;
    private TLVector<TLAbsInputUser> users;
    private String title;

    @Override
    public int getClassId() {
        return 164303470;
    }

    @Override
    public TLAbsUpdates deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsUpdates) {
            return (TLAbsUpdates)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.updates.TLAbsUpdates, got: " + res.getClass().getCanonicalName());
    }

    public TLVector<TLAbsInputUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsInputUser> value) {
        this.users = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.users, stream);
        StreamingUtils.writeTLString(this.title, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.users = StreamingUtils.readTLVector(stream, context);
        this.title = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "messages.createChat#9cb126e";
    }
}

