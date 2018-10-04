
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.input.TLInputPhoneContact;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestContactsImportContacts
extends TLMethod<TLImportedContacts> {
    public static final int CLASS_ID = -634342611;
    private TLVector<TLInputPhoneContact> contacts;
    private boolean replace;

    @Override
    public int getClassId() {
        return -634342611;
    }

    @Override
    public TLImportedContacts deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLImportedContacts) {
            return (TLImportedContacts)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.contacts.TLImportedContacts, got: " + res.getClass().getCanonicalName());
    }

    public TLVector<TLInputPhoneContact> getContacts() {
        return this.contacts;
    }

    public void setContacts(TLVector<TLInputPhoneContact> value) {
        this.contacts = value;
    }

    public boolean getReplace() {
        return this.replace;
    }

    public void setReplace(boolean value) {
        this.replace = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.contacts, stream);
        StreamingUtils.writeTLBool(this.replace, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.contacts = StreamingUtils.readTLVector(stream, context);
        this.replace = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "contacts.importContacts#da30b32d";
    }
}

