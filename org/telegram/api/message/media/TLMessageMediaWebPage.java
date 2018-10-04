
package org.telegram.api.message.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.message.media.TLAbsMessageMedia;
import org.telegram.api.webpage.TLAbsWebPage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLMessageMediaWebPage
extends TLAbsMessageMedia {
    public static final int CLASS_ID = -1557277184;
    private TLAbsWebPage webPage;

    @Override
    public int getClassId() {
        return -1557277184;
    }

    public TLAbsWebPage getWebPage() {
        return this.webPage;
    }

    public void setWebPage(TLAbsWebPage webPage) {
        this.webPage = webPage;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(this.webPage, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.webPage = (TLAbsWebPage)StreamingUtils.readTLObject(stream, context);
    }

    @Override
    public String toString() {
        return "messageMediaDocument#a32dd600";
    }
}

