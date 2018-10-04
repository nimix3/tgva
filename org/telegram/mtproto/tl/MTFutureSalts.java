
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.tl.MTFutureSalt;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class MTFutureSalts
extends TLObject {
    public static final int CLASS_ID = -1370486635;
    private long requestId;
    private int now;
    private TLVector<MTFutureSalt> salts = new TLVector();

    public MTFutureSalts(long requestId, int now, TLVector<MTFutureSalt> salts) {
        this.requestId = requestId;
        this.now = now;
        this.salts = salts;
    }

    public MTFutureSalts() {
    }

    @Override
    public int getClassId() {
        return -1370486635;
    }

    public long getRequestId() {
        return this.requestId;
    }

    public int getNow() {
        return this.now;
    }

    public TLVector<MTFutureSalt> getSalts() {
        return this.salts;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeLong(this.requestId, stream);
        StreamingUtils.writeInt(this.now, stream);
        StreamingUtils.writeInt(this.salts.size(), stream);
        for (MTFutureSalt salt : this.salts) {
            salt.serializeBody(stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.requestId = StreamingUtils.readLong(stream);
        this.now = StreamingUtils.readInt(stream);
        int count = StreamingUtils.readInt(stream);
        this.salts.clear();
        for (int i = 0; i < count; ++i) {
            MTFutureSalt salt = new MTFutureSalt();
            salt.deserializeBody(stream, context);
            this.salts.add(salt);
        }
    }

    @Override
    public String toString() {
        return "future_salts#ae500895";
    }
}

