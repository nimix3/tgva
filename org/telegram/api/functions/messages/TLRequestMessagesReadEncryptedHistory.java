
package org.telegram.api.functions.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.encrypted.TLInputEncryptedChat;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestMessagesReadEncryptedHistory
extends TLMethod<TLBool> {
    public static final int CLASS_ID = 2135648522;
    private TLInputEncryptedChat peer;
    private int maxDate;

    @Override
    public int getClassId() {
        return 2135648522;
    }

    @Override
    public TLBool deserializeResponse(InputStream stream, TLContext context) throws IOException {
        TLObject res = StreamingUtils.readTLObject(stream, context);
        if (res == null) {
            throw new IOException("Unable to parse response");
        }
        if (res instanceof TLBool) {
            return (TLBool)res;
        }
        throw new IOException("Incorrect response type. Expected org.telegram.tl.TLBool, got: " + res.getClass().getCanonicalName());
    }

    public TLInputEncryptedChat getPeer() {
        return this.peer;
    }

    public void setPeer(TLInputEncryptedChat value) {
        this.peer = value;
    }

    public int getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(int value) {
        this.maxDate = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.peer, stream);
        StreamingUtils.writeInt(this.maxDate, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.peer = (TLInputEncryptedChat)StreamingUtils.readTLObject(stream, context);
        this.maxDate = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "messages.readEncryptedHistory#7f4b690a";
    }
}

