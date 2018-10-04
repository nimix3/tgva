
package org.telegram.mtproto.transport;

import org.telegram.mtproto.transport.TcpContext;

public interface TcpContextCallback {
    public void onRawMessage(byte[] var1, int var2, int var3, TcpContext var4);

    public void onError(int var1, TcpContext var2);

    public void onChannelBroken(TcpContext var1);

    public void onFastConfirm(int var1);
}

