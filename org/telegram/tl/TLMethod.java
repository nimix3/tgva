
package org.telegram.tl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public abstract class TLMethod<T extends TLObject>
extends TLObject {
    public T deserializeResponse(byte[] data, TLContext context) throws IOException {
        return this.deserializeResponse(new ByteArrayInputStream(data), context);
    }

    public T castResponse(TLObject response) {
        return (T)response;
    }

    public abstract T deserializeResponse(InputStream var1, TLContext var2) throws IOException;
}

