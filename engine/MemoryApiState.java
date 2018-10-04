
package engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.telegram.api.TLConfig;
import org.telegram.api.TLDcOption;
import org.telegram.api.engine.storage.AbsApiState;
import org.telegram.mtproto.state.AbsMTProtoState;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.state.KnownSalt;
import org.telegram.tl.TLVector;

public class MemoryApiState
implements AbsApiState,
Serializable {
    private HashMap<Integer, ConnectionInfo[]> connections = new HashMap();
    private HashMap<Integer, byte[]> keys = new HashMap();
    private HashMap<Integer, Boolean> isAuth = new HashMap();
    private int primaryDc = 1;

    public MemoryApiState(boolean isTest) {
        this.connections.put(1, new ConnectionInfo[]{new ConnectionInfo(1, 0, "149.154.175.50", 80), new ConnectionInfo(2, 0, "149.154.167.51", 80), new ConnectionInfo(3, 0, "149.154.175.100", 80), new ConnectionInfo(4, 0, "149.154.167.91", 80)});
    }

    @Override
    public synchronized int getPrimaryDc() {
        return this.primaryDc;
    }

    @Override
    public synchronized void setPrimaryDc(int dc) {
        this.primaryDc = dc;
    }

    @Override
    public synchronized boolean isAuthenticated(int dcId) {
        if (this.isAuth.containsKey(dcId)) {
            return this.isAuth.get(dcId);
        }
        return false;
    }

    @Override
    public synchronized void setAuthenticated(int dcId, boolean auth) {
        this.isAuth.put(dcId, auth);
    }

    @Override
    public synchronized void updateSettings(TLConfig config) {
        this.connections.clear();
        HashMap tConnections = new HashMap();
        int id = 0;
        for (TLDcOption option : config.getDcOptions()) {
            if (!tConnections.containsKey(option.getId())) {
                tConnections.put(option.getId(), new ArrayList());
            }
            ((ArrayList)tConnections.get(option.getId())).add(new ConnectionInfo(id++, 0, option.getIpAddress(), option.getPort()));
        }
        for (Integer dc : tConnections.keySet()) {
            this.connections.put(dc, ((ArrayList)tConnections.get(dc)).toArray(new ConnectionInfo[0]));
        }
    }

    @Override
    public synchronized byte[] getAuthKey(int dcId) {
        return this.keys.get(dcId);
    }

    @Override
    public synchronized void putAuthKey(int dcId, byte[] key) {
        this.keys.put(dcId, key);
    }

    @Override
    public synchronized ConnectionInfo[] getAvailableConnections(int dcId) {
        if (!this.connections.containsKey(dcId)) {
            return new ConnectionInfo[0];
        }
        return this.connections.get(dcId);
    }

    @Override
    public synchronized AbsMTProtoState getMtProtoState(final int dcId) {
        return new AbsMTProtoState(){
            private KnownSalt[] knownSalts = new KnownSalt[0];

            @Override
            public byte[] getAuthKey() {
                return MemoryApiState.this.getAuthKey(dcId);
            }

            @Override
            public ConnectionInfo[] getAvailableConnections() {
                return MemoryApiState.this.getAvailableConnections(dcId);
            }

            @Override
            public KnownSalt[] readKnownSalts() {
                return this.knownSalts;
            }

            @Override
            protected void writeKnownSalts(KnownSalt[] salts) {
                this.knownSalts = salts;
            }
        };
    }

    @Override
    public synchronized void resetAuth() {
        this.isAuth.clear();
    }

    @Override
    public synchronized void reset() {
        this.isAuth.clear();
        this.keys.clear();
    }

}

