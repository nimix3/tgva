
package org.telegram.api.engine.storage;

import org.telegram.api.TLConfig;
import org.telegram.mtproto.state.AbsMTProtoState;
import org.telegram.mtproto.state.ConnectionInfo;

public interface AbsApiState {
    public int getPrimaryDc();

    public void setPrimaryDc(int var1);

    public boolean isAuthenticated(int var1);

    public void setAuthenticated(int var1, boolean var2);

    public void updateSettings(TLConfig var1);

    public byte[] getAuthKey(int var1);

    public void putAuthKey(int var1, byte[] var2);

    public ConnectionInfo[] getAvailableConnections(int var1);

    public AbsMTProtoState getMtProtoState(int var1);

    public void resetAuth();

    public void reset();
}

