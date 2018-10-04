
package jawnae.pyronet;

import java.io.IOException;
import java.nio.ByteBuffer;
import jawnae.pyronet.PyroClient;

public interface PyroClientListener {
    public void connectedClient(PyroClient var1);

    public void unconnectableClient(PyroClient var1, Exception var2);

    public void droppedClient(PyroClient var1, IOException var2);

    public void disconnectedClient(PyroClient var1);

    public void receivedData(PyroClient var1, ByteBuffer var2);

    public void sentData(PyroClient var1, int var2);
}

