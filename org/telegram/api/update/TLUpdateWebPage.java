
package org.telegram.api.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.webpage.TLAbsWebPage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLUpdateWebPage
extends TLAbsUpdate {
    public static final int CLASS_ID = 2139689491;
    private TLAbsWebPage webPage;
    private int pts;
    private int ptsCount;

    @Override
    public int getClassId() {
        return 2139689491;
    }

    public TLAbsWebPage getWebPage() {
        return this.webPage;
    }

    public void setWebPage(TLAbsWebPage webPage) {
        this.webPage = webPage;
    }

    @Override
    public int getPts() {
        return this.pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    @Override
    public int getPtsCount() {
        return this.ptsCount;
    }

    public void setPtsCount(int ptsCount) {
        this.ptsCount = ptsCount;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.webPage, stream);
        StreamingUtils.writeInt(this.pts, stream);
        StreamingUtils.writeInt(this.ptsCount, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.webPage = (TLAbsWebPage)StreamingUtils.readTLObject(stream, context);
        this.pts = StreamingUtils.readInt(stream);
        this.ptsCount = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "updateWebPage#7f891213";
    }
}

