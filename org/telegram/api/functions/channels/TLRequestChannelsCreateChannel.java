
package org.telegram.api.functions.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestChannelsCreateChannel
extends TLMethod<TLAbsUpdates> {
    public static final int CLASS_ID = -192332417;
    private static final int FLAG_BROADCAST = 1;
    private static final int FLAG_MEGAGROUP = 2;
    private int flags;
    private String title;
    private String about;

    public void setcomputeFlags(boolean broadcast, boolean megagroup) {
        this.flags = 0;
        this.flags = broadcast ? this.flags | 1 : this.flags & -2;
        this.flags = megagroup ? this.flags | 2 : this.flags & -3;
    }

    @Override
    public int getClassId() {
        return -192332417;
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
        throw new IOException("Incorrect response type. Expected " + TLAbsUpdates.class.getName() + ", got: " + res.getClass().getName());
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.about, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.about = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "channels.createChannel#f4893d7f";
    }
}

