
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TLRequestContactsExportCard
extends TLMethod<TLIntVector> {
    public static final int CLASS_ID = -2065352905;

    @Override
    public int getClassId() {
        return -2065352905;
    }

    @Override
    public TLIntVector deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLIntVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.exportCard#84e53737";
    }
}

