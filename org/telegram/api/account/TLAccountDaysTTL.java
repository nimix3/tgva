
package org.telegram.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLAccountDaysTTL
extends TLObject {
    public static final int CLASS_ID = -1194283041;
    private int days;

    @Override
    public int getClassId() {
        return -1194283041;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.days, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.days = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "account.accountDaysTLL#b8d0afdf";
    }
}

