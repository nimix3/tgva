
package org.telegram.api.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contact.TLImportedContact;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLImportedContacts
extends TLObject {
    public static final int CLASS_ID = -1387117803;
    private TLVector<TLImportedContact> imported;
    private TLLongVector retryContacts = new TLLongVector();
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return -1387117803;
    }

    public TLVector<TLImportedContact> getImported() {
        return this.imported;
    }

    public void setImported(TLVector<TLImportedContact> value) {
        this.imported = value;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> value) {
        this.users = value;
    }

    public TLLongVector getRetryContacts() {
        return this.retryContacts;
    }

    public void setRetryContacts(TLLongVector retryContacts) {
        this.retryContacts = retryContacts;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.imported, stream);
        StreamingUtils.writeTLVector(this.retryContacts, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.imported = StreamingUtils.readTLVector(stream, context);
        this.retryContacts = StreamingUtils.readTLLongVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.importedContacts#ad524315";
    }
}

