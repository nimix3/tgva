
package org.telegram.mtproto.transport;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.transport.ConnectionType;

public class TransportRate {
    private static final String TAG = "TransportRate";
    private HashMap<Integer, Transport> transports = new HashMap();
    private Random rnd = new Random();

    public TransportRate(ConnectionInfo[] connectionInfos) {
        int i;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (i = 0; i < connectionInfos.length; ++i) {
            min = Math.min(connectionInfos[i].getPriority(), min);
            max = Math.max(connectionInfos[i].getPriority(), max);
        }
        for (i = 0; i < connectionInfos.length; ++i) {
            this.transports.put(connectionInfos[i].getId(), new Transport(new ConnectionType(connectionInfos[i].getId(), connectionInfos[i].getAddress(), connectionInfos[i].getPort(), 0), connectionInfos[i].getPriority() - min + 1));
        }
        this.normalize();
    }

    public synchronized ConnectionType tryConnection() {
        float value = this.rnd.nextFloat();
        Transport[] currentTransports = this.transports.values().toArray(new Transport[0]);
        Arrays.sort(currentTransports, new Comparator<Transport>(){

            @Override
            public int compare(Transport transport, Transport transport2) {
                return - Float.compare(transport.getRate(), transport2.getRate());
            }
        });
        ConnectionType type = currentTransports[0].getConnectionType();
        Logger.d(TAG, "tryConnection #" + type.getId());
        return type;
    }

    public synchronized void onConnectionFailure(int id) {
        Logger.d(TAG, "onConnectionFailure #" + id);
        Transport transport = this.transports.get(id);
        transport.rate = (float)((double)transport.rate * 0.5);
        this.normalize();
    }

    public synchronized void onConnectionSuccess(int id) {
        Logger.d(TAG, "onConnectionSuccess #" + id);
        Transport transport = this.transports.get(id);
        transport.rate = (float)((double)transport.rate * 1.0);
        this.normalize();
    }

    private synchronized void normalize() {
        float sum = 0.0f;
        for (Integer id : this.transports.keySet()) {
            sum += this.transports.get(id).rate;
        }
        for (Integer id : this.transports.keySet()) {
            Transport transport;
            Transport transport2 = transport = this.transports.get(id);
            transport2.rate = transport2.rate / sum;
            Logger.d(TAG, "Transport: #" + transport.connectionType.getId() + " " + transport.connectionType.getHost() + ":" + transport.getConnectionType().getPort() + " #" + transport.getRate());
        }
    }

    private class Transport {
        private ConnectionType connectionType;
        private float rate;

        private Transport(ConnectionType connectionType, float rate) {
            this.connectionType = connectionType;
            this.rate = rate;
        }

        public ConnectionType getConnectionType() {
            return this.connectionType;
        }

        public void setConnectionType(ConnectionType connectionType) {
            this.connectionType = connectionType;
        }

        public float getRate() {
            return this.rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }
    }

}

