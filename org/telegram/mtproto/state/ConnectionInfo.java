
package org.telegram.mtproto.state;

import java.io.Serializable;

public class ConnectionInfo
implements Serializable {
    private int id;
    private int priority;
    private String address;
    private int port;

    public ConnectionInfo(int id, int priority, String address, int port) {
        this.id = id;
        this.priority = priority;
        this.address = address;
        this.port = port;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    public int getId() {
        return this.id;
    }
}

