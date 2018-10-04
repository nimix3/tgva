
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;

public class TLUpdateServiceNotification
extends TLAbsUpdate {
    public static final int CLASS_ID = 942527460;
    private String type;
    private boolean popup;
    private TLAbsMessageMedia media;
    private TLIntVector messages;

    @Override
    public int getClassId() {
        return 942527460;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPopup() {
        return this.popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public TLAbsMessageMedia getMedia() {
        return this.media;
    }

    public void setMedia(TLAbsMessageMedia media) {
        this.media = media;
    }

    public TLIntVector getMessages() {
        return this.messages;
    }

    public void setMessages(TLIntVector messages) {
        this.messages = messages;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.type, stream);
        StreamingUtils.writeTLVector(this.messages, stream);
        StreamingUtils.writeTLObject(this.media, stream);
        StreamingUtils.writeTLBool(this.popup, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.type = StreamingUtils.readTLString(stream);
        this.messages = StreamingUtils.readTLIntVector(stream, context);
        this.media = (TLAbsMessageMedia)StreamingUtils.readTLObject(stream, context);
        this.popup = StreamingUtils.readTLBool(stream);
    }

    @Override
    public String toString() {
        return "updateServiceNotification#382dd3e4";
    }
}

