
package org.telegram.api.engine;

import org.telegram.tl.TLObject;

public interface RpcCallback<T extends TLObject> {
    public void onResult(T var1);

    public void onError(int var1, String var2);
}

