
package org.telegram.api.contacts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contact.link.TLAbsContactLink;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLContactsLink
extends TLObject {
    public static final int CLASS_ID = 986597452;
    private TLAbsContactLink myLink;
    private TLAbsContactLink foreignLink;
    private TLAbsUser user;

    @Override
    public int getClassId() {
        return 986597452;
    }

    public TLAbsContactLink getMyLink() {
        return this.myLink;
    }

    public void setMyLink(TLAbsContactLink value) {
        this.myLink = value;
    }

    public TLAbsContactLink getForeignLink() {
        return this.foreignLink;
    }

    public void setForeignLink(TLAbsContactLink value) {
        this.foreignLink = value;
    }

    public TLAbsUser getUser() {
        return this.user;
    }

    public void setUser(TLAbsUser value) {
        this.user = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.myLink, stream);
        StreamingUtils.writeTLObject(this.foreignLink, stream);
        StreamingUtils.writeTLObject(this.user, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.myLink = (TLAbsContactLink)StreamingUtils.readTLObject(stream, context);
        this.foreignLink = (TLAbsContactLink)StreamingUtils.readTLObject(stream, context);
        this.user = (TLAbsUser)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.link#3ace484c";
    }
}

