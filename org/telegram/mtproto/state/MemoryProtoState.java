
package org.telegram.mtproto.state;

import org.telegram.mtproto.state.AbsMTProtoState;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.state.KnownSalt;

public class MemoryProtoState
extends AbsMTProtoState {
    private KnownSalt[] salts = new KnownSalt[0];
    private String address;
    private int port;
    private byte[] authKey;

    public MemoryProtoState(byte[] authKey, String address, int port) {
        this.authKey = authKey;
        this.port = port;
        this.address = address;
    }

    @Override
    public byte[] getAuthKey() {
        return this.authKey;
    }

    @Override
    public ConnectionInfo[] getAvailableConnections() {
        return new ConnectionInfo[]{new ConnectionInfo(0, 0, this.address, this.port)};
    }

    @Override
    public KnownSalt[] readKnownSalts() {
        return this.salts;
    }

    @Override
    protected void writeKnownSalts(KnownSalt[] salts) {
        this.salts = salts;
    }
}

