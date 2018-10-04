
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.encrypted.chat.TLAbsEncryptedChat;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesRequestEncryption
extends TLMethod<TLAbsEncryptedChat> {
    public static final int CLASS_ID = -162681021;
    private TLAbsInputUser userId;
    private int randomId;
    private TLBytes gA;

    @Override
    public int getClassId() {
        return -162681021;
    }

    @Override
    public TLAbsEncryptedChat deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLAbsEncryptedChat) {
            return (TLAbsEncryptedChat)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.api.encrypted.chat.TLAbsEncryptedChat, got: " + res.getClass().getCanonicalName());
    }

    public TLAbsInputUser getUserId() {
        return this.userId;
    }

    public void setUserId(TLAbsInputUser value) {
        this.userId = value;
    }

    public int getRandomId() {
        return this.randomId;
    }

    public void setRandomId(int value) {
        this.randomId = value;
    }

    public TLBytes getGA() {
        return this.gA;
    }

    public void setGA(TLBytes value) {
        this.gA = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.userId, stream);
        StreamingUtils.writeInt(this.randomId, stream);
        StreamingUtils.writeTLBytes(this.gA, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.userId = (TLAbsInputUser)StreamingUtils.readTLObject(stream, context);
        this.randomId = StreamingUtils.readInt(stream);
        this.gA = StreamingUtils.readTLBytes(stream, context);
    }

    @Override
    public String toString() {
        return "messages.requestEncryption#f64daf43";
    }
}

