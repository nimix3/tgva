
package org.telegram.api.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contact.TLContact;
import org.telegram.api.contacts.TLAbsContacts;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLContacts
extends TLAbsContacts {
    public static final int CLASS_ID = 1871416498;
    private TLVector<TLContact> contacts;
    private TLVector<TLAbsUser> users;

    @Override
    public int getClassId() {
        return 1871416498;
    }

    public TLVector<TLContact> getContacts() {
        return this.contacts;
    }

    public void setContacts(TLVector<TLContact> contacts) {
        this.contacts = contacts;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> users) {
        this.users = users;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.contacts, stream);
        StreamingUtils.writeTLVector(this.users, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.contacts = StreamingUtils.readTLVector(stream, context);
        this.users = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.contacts#6f8b8cb2";
    }
}

