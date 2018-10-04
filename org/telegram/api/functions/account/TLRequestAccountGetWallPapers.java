
package org.telegram.api.functions.account;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.wallpaper.TLAbsWallPaper;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestAccountGetWallPapers
extends TLMethod<TLVector<TLAbsWallPaper>> {
    public static final int CLASS_ID = -1068696894;

    @Override
    public int getClassId() {
        return -1068696894;
    }

    @Override
    public TLVector<TLAbsWallPaper> deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "account.getWallPapers#c04cfac2";
    }
}

