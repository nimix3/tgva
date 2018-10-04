
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.TLContactsLink;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsDeleteContact
extends TLMethod<TLContactsLink> {
    public static final int CLASS_ID = -1902823612;
    private TLAbsInputUser id;

    @Override
    public int getClassId() {
        return -1902823612;
    }

    @Override
    public TLContactsLink deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLContactsLink) {
            return (TLContactsLink)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.contacts.TLLink, got: " + res.getClass().getCanonicalName());
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
        return "contacts.deleteContact#8e953744";
    }
}

