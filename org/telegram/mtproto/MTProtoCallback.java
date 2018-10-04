
package org.telegram.mtproto;

import org.telegram.mtproto.MTProto;

public interface MTProtoCallback {
    public void onSessionCreated(MTProto var1);

    public void onAuthInvalidated(MTProto var1);

    public void onApiMessage(byte[] var1, MTProto var2);

    public void onRpcResult(int var1, byte[] var2, MTProto var3);

    public void onRpcError(int var1, int var2, String var3, MTProto var4);

    public void onConfirmed(int var1);
}

