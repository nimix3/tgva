
package org.telegram.api.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInputAppEvent
extends TLObject {
    public static final int CLASS_ID = 1996904104;
    private double time;
    private String type;
    private long peer;
    private String data;

    @Override
    public int getClassId() {
        return 1996904104;
    }

    public double getTime() {
        return this.time;
    }

    public void setTime(double value) {
        this.time = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public long getPeer() {
        return this.peer;
    }

    public void setPeer(long value) {
        this.peer = value;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String value) {
        this.data = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeDouble(this.time, stream);
        StreamingUtils.writeTLString(this.type, stream);
        StreamingUtils.writeLong(this.peer, stream);
        StreamingUtils.writeTLString(this.data, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.time = StreamingUtils.readDouble(stream);
        this.type = StreamingUtils.readTLString(stream);
        this.peer = StreamingUtils.readLong(stream);
        this.data = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "inputAppEvent#770656a8";
    }
}

