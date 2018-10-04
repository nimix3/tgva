
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.TLAbsContacts;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsGetContacts
extends TLMethod<TLAbsContacts> {
    public static final int CLASS_ID = 583445000;
    private String hash;

    @Override
    public int getClassId() {
        return 583445000;
    }

    @Override
    public TLAbsContacts deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsContacts) {
            return (TLAbsContacts)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.contacts.TLAbsContacts, got: " + res.getClass().getCanonicalName());
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String value) {
        this.hash = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.hash, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.hash = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "contacts.getContacts#22c6aa08";
    }
}

