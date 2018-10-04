
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.contact.link.TLAbsContactLink;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateContactLink
extends TLAbsUpdate {
    public static final int CLASS_ID = -1657903163;
    private int userId;
    private TLAbsContactLink foreignLink;
    private TLAbsContactLink myLink;

    @Override
    public int getClassId() {
        return -1657903163;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TLAbsContactLink getForeignLink() {
        return this.foreignLink;
    }

    public void setForeignLink(TLAbsContactLink foreignLink) {
        this.foreignLink = foreignLink;
    }

    public TLAbsContactLink getMyLink() {
        return this.myLink;
    }

    public void setMyLink(TLAbsContactLink myLink) {
        this.myLink = myLink;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.userId, stream);
        StreamingUtils.writeTLObject(this.myLink, stream);
        StreamingUtils.writeTLObject(this.foreignLink, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = StreamingUtils.readInt(stream);
        this.myLink = (TLAbsContactLink)StreamingUtils.readTLObject(stream, context);
        this.foreignLink = (TLAbsContactLink)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "updateContactLink#9d2e67c5";
    }
}

