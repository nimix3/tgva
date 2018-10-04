
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTGetFutureSalts
extends TLObject {
    public static final int CLASS_ID = -1188971260;
    private int num;

    public MTGetFutureSalts(int num) {
        this.num = num;
    }

    public MTGetFutureSalts() {
    }

    @Override
    public int getClassId() {
        return -1188971260;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.num, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.num = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "get_future_salts#b921bd04";
    }
}

