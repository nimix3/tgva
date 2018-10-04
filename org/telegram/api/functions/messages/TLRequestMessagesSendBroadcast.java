
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestMessagesSendBroadcast
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -1082919718;
    private TLVector<TLAbsInputUser> contacts;
    private String message;
    private TLAbsInputMedia media;
    private TLLongVector randomId;

    @Override
    public int getClassId() {
        return -1082919718;
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

    public TLVector<TLAbsInputUser> getContacts() {
        return this.contacts;
    }

    public void setContacts(TLVector<TLAbsInputUser> value) {
        this.contacts = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public TLAbsInputMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsInputMedia value) {
        this.media = value;
    }

    public TLLongVector getRandomId() {
        return this.randomId;
    }

    public void setRandomId(TLLongVector randomId) {
        this.randomId = randomId;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLVector(this.contacts, stream);
        StreamingUtils.writeTLVector(this.randomId, stream);
        StreamingUtils.writeTLString(this.message, stream);
        StreamingUtils.writeTLObject(this.media, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.contacts = StreamingUtils.readTLVector(stream, context);
        this.randomId = StreamingUtils.readTLLongVector(stream, context);
        this.message = StreamingUtils.readTLString(stream);
        this.media = (TLAbsInputMedia)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messages.sendBroadcast#bf73f4da";
    }
}

