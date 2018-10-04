
package org.telegram.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLDcOption
extends TLObject {
    public static final int CLASS_ID = 98092748;
    private static final int FLAG_IPV6 = 1;
    private static final int FLAG_MEDIA_ONLY = 2;
    private static final int FLAG_TCP_ONLY = 4;
    private int flags;
    private int id;
    private String ipAddress;
    private int port;

    @Override
    public int getClassId() {
        return 98092748;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int value) {
        this.port = value;
    }

    private boolean isIPV6() {
        return (this.flags & 1) != 0;
    }

    private boolean isMediaOnly() {
        return (this.flags & 2) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.ipAddress, stream);
        StreamingUtils.writeInt(this.port, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readInt(stream);
        this.ipAddress = StreamingUtils.readTLString(stream);
        this.port = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "dcOption#5d8c6cc";
    }
}

