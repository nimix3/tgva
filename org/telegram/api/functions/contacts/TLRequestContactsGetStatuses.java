
package org.telegram.api.functions.contacts;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.api.contact.TLContactStatus;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class TLRequestContactsGetStatuses
extends TLMethod<TLVector<TLContactStatus>> {
    public static final int CLASS_ID = -995929106;

    @Override
    public int getClassId() {
        return -995929106;
    }

    @Override
    public TLVector<TLContactStatus> deserializeResponse(InputStream stream, TLContext context) throws IOException {
        return StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "contacts.getStatuses#c4a353ee";
    }
}

