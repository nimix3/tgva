
package jawnae.pyronet;

import java.io.IOException;
import java.nio.ByteBuffer;
import jawnae.pyronet.PyroClient;
import jawnae.pyronet.PyroClientListener;

public class PyroClientAdapter
implements PyroClientListener {
    private static final String LOGTAG = "PYROCLIENTADAPTER";

    @Override
    public void connectedClient(PyroClient client) {
    }

    @Override
    public void unconnectableClient(PyroClient client, Exception cause) {
    }

    public void unconnectableClient(PyroClient client) {
    }

    @Override
    public void droppedClient(PyroClient client, IOException cause) {
        if (cause != null) {
            // empty if block
        }
    }

    @Override
    public void disconnectedClient(PyroClient client) {
    }

    @Override
    public void receivedData(PyroClient client, ByteBuffer data) {
    }

    @Override
    public void sentData(PyroClient client, int bytes) {
    }
}

