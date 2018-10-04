
package org.telegram.api.decryptedmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.decryptedmessage.TLDecryptedMessageAction;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLDecryptedMessageActionNotifyLayer
extends TLDecryptedMessageAction {
    public static final int CLASS_ID = -217806717;
    private int layer = 22;

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.layer, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.layer = StreamingUtils.readInt(stream);
    }

    @Override
    public int getClassId() {
        return -217806717;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        return "decryptedMessageActionNotifyLayer#f3048883";
    }
}

