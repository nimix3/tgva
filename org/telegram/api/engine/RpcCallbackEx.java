
package org.telegram.api.engine;

import org.telegram.api.engine.RpcCallback;
import org.telegram.tl.TLObject;

public interface RpcCallbackEx<T extends TLObject>
extends RpcCallback<T> {
    public void onConfirmed();
}

