
package jawnae.pyronet;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import jawnae.pyronet.ByteStream;
import jawnae.pyronet.PyroClientListener;
import jawnae.pyronet.PyroException;
import jawnae.pyronet.PyroSelector;
import org.telegram.mtproto.transport.ByteBufferDesc;

public class PyroClient {
    private final PyroSelector selector;
    private final SelectionKey key;
    private final ByteStream outbound;
    private final List<PyroClientListener> listeners;
    private Object attachment;
    private boolean doEagerWrite = false;
    private boolean doShutdown = false;
    private long timeout = 0L;
    private long lastEventTime;

    PyroClient(PyroSelector selector, InetSocketAddress bind, InetSocketAddress host) throws IOException {
        this(selector, PyroClient.bindAndConfigure(selector, SocketChannel.open(), bind));
        ((SocketChannel)this.key.channel()).connect(host);
    }

    PyroClient(PyroSelector selector, SelectionKey key) {
        this.selector = selector;
        this.selector.checkThread();
        this.key = key;
        this.key.attach(this);
        this.outbound = new ByteStream();
        this.listeners = new CopyOnWriteArrayList<PyroClientListener>();
        this.lastEventTime = System.currentTimeMillis();
    }

    public void addListener(PyroClientListener listener) {
        this.selector.checkThread();
        this.listeners.add(listener);
    }

    public void removeListener(PyroClientListener listener) {
        this.selector.checkThread();
        this.listeners.remove(listener);
    }

    public void removeListeners() {
        this.selector.checkThread();
        this.listeners.clear();
    }

    public PyroSelector selector() {
        return this.selector;
    }

    public void attach(Object attachment) {
        this.attachment = attachment;
    }

    public <T> T attachment() {
        return (T)this.attachment;
    }

    public InetSocketAddress getLocalAddress() {
        Socket s = ((SocketChannel)this.key.channel()).socket();
        return (InetSocketAddress)s.getLocalSocketAddress();
    }

    public InetSocketAddress getRemoteAddress() {
        Socket s = ((SocketChannel)this.key.channel()).socket();
        return (InetSocketAddress)s.getRemoteSocketAddress();
    }

    public void setTimeout(int ms) throws IOException {
        this.selector.checkThread();
        ((SocketChannel)this.key.channel()).socket().setSoTimeout(ms);
        this.lastEventTime = System.currentTimeMillis();
        this.timeout = ms;
    }

    public void setLinger(boolean enabled, int seconds) throws IOException {
        this.selector.checkThread();
        ((SocketChannel)this.key.channel()).socket().setSoLinger(enabled, seconds);
    }

    public void setKeepAlive(boolean enabled) throws IOException {
        this.selector.checkThread();
        ((SocketChannel)this.key.channel()).socket().setKeepAlive(enabled);
    }

    public void setEagerWrite(boolean enabled) {
        this.doEagerWrite = enabled;
    }

    public void write(ByteBufferDesc data) throws PyroException {
        this.selector.checkThread();
        if (!this.key.isValid()) {
            return;
        }
        if (this.doShutdown) {
            throw new PyroException("shutting down");
        }
        this.outbound.append(data);
        if (this.doEagerWrite) {
            try {
                this.onReadyToWrite(System.currentTimeMillis());
            }
            catch (NotYetConnectedException exc) {
                this.adjustWriteOp();
            }
            catch (IOException exc) {
                this.onConnectionError(exc);
                this.key.cancel();
            }
        } else {
            this.adjustWriteOp();
        }
    }

    public int flush() {
        int total = 0;
        while (this.outbound.hasData()) {
            int written;
            try {
                written = this.onReadyToWrite(System.currentTimeMillis());
            }
            catch (IOException exc) {
                written = 0;
            }
            if (written == 0) break;
            total += written;
        }
        return total;
    }

    public int flushOrDie() throws PyroException {
        int total = 0;
        while (this.outbound.hasData()) {
            int written;
            try {
                written = this.onReadyToWrite(System.currentTimeMillis());
            }
            catch (IOException exc) {
                written = 0;
            }
            if (written == 0) {
                throw new PyroException("failed to flush, wrote " + total + " bytes");
            }
            total += written;
        }
        return total;
    }

    public boolean hasDataEnqueued() {
        this.selector.checkThread();
        return this.outbound.hasData();
    }

    public void shutdown() {
        this.selector.checkThread();
        this.doShutdown = true;
        if (!this.hasDataEnqueued()) {
            this.dropConnection();
        }
    }

    public void dropConnection() {
        this.selector.checkThread();
        if (this.isDisconnected()) {
            return;
        }
        Runnable drop = new Runnable(){

            @Override
            public void run() {
                try {
                    if (PyroClient.this.key.channel().isOpen()) {
                        PyroClient.this.key.channel().close();
                    }
                }
                catch (IOException exc) {
                    PyroClient.this.selector().scheduleTask(this);
                }
            }
        };
        drop.run();
        this.onConnectionError("local");
    }

    public boolean isDisconnected() {
        this.selector.checkThread();
        return !this.key.channel().isOpen();
    }

    void onInterestOp(long now) {
        if (!this.key.isValid()) {
            this.onConnectionError("remote");
        } else {
            try {
                if (this.key.isConnectable()) {
                    this.onReadyToConnect(now);
                }
                if (this.key.isReadable()) {
                    this.onReadyToRead(now);
                }
                if (this.key.isWritable()) {
                    this.onReadyToWrite(now);
                }
            }
            catch (IOException exc) {
                this.onConnectionError(exc);
                this.key.cancel();
            }
        }
    }

    boolean didTimeout(long now) {
        return this.timeout != 0L && now - this.lastEventTime > this.timeout;
    }

    private void onReadyToConnect(long now) throws IOException {
        this.selector.checkThread();
        this.lastEventTime = now;
        this.selector.adjustInterestOp(this.key, 8, false);
        ((SocketChannel)this.key.channel()).finishConnect();
        for (PyroClientListener listener : this.listeners) {
            listener.connectedClient(this);
        }
    }

    private void onReadyToRead(long now) throws IOException {
        this.selector.checkThread();
        this.lastEventTime = now;
        SocketChannel channel = (SocketChannel)this.key.channel();
        ByteBuffer buffer = this.selector.networkBuffer;
        buffer.clear();
        int bytes = channel.read(buffer);
        if (bytes == -1) {
            throw new EOFException();
        }
        buffer.flip();
        for (PyroClientListener listener : this.listeners) {
            listener.receivedData(this, buffer);
        }
    }

    private int onReadyToWrite(long now) throws IOException {
        this.selector.checkThread();
        int sent = 0;
        ByteBuffer buffer = this.selector.networkBuffer;
        buffer.clear();
        this.outbound.get(buffer);
        buffer.flip();
        if (buffer.hasRemaining()) {
            SocketChannel channel = (SocketChannel)this.key.channel();
            sent = channel.write(buffer);
        }
        if (sent > 0) {
            this.outbound.discard(sent);
        }
        for (PyroClientListener listener : this.listeners) {
            listener.sentData(this, sent);
        }
        this.adjustWriteOp();
        if (this.doShutdown && !this.outbound.hasData()) {
            this.dropConnection();
        }
        return sent;
    }

    void onConnectionError(final Object cause) {
        this.selector.checkThread();
        try {
            this.key.channel().close();
        }
        catch (IOException exc) {
            this.selector.scheduleTask(new Runnable(){

                @Override
                public void run() {
                    PyroClient.this.onConnectionError(cause);
                }
            });
            return;
        }
        if (cause instanceof ConnectException) {
            for (PyroClientListener listener : this.listeners) {
                listener.unconnectableClient(this, (Exception)cause);
            }
        } else if (cause instanceof EOFException) {
            for (PyroClientListener listener : this.listeners) {
                listener.disconnectedClient(this);
            }
        } else if (cause instanceof IOException) {
            for (PyroClientListener listener : this.listeners) {
                listener.droppedClient(this, (IOException)cause);
            }
        } else if (!(cause instanceof String)) {
            for (PyroClientListener listener : this.listeners) {
                listener.unconnectableClient(this, null);
            }
        } else if (cause.equals("local")) {
            for (PyroClientListener listener : this.listeners) {
                listener.disconnectedClient(this);
            }
        } else if (cause.equals("remote")) {
            for (PyroClientListener listener : this.listeners) {
                listener.droppedClient(this, null);
            }
        } else {
            throw new IllegalStateException("illegal cause: " + cause);
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getAddressText() + "]";
    }

    private final String getAddressText() {
        if (!this.key.channel().isOpen()) {
            return "closed";
        }
        InetSocketAddress sockaddr = this.getRemoteAddress();
        if (sockaddr == null) {
            return "connecting";
        }
        InetAddress inetaddr = sockaddr.getAddress();
        return inetaddr.getHostAddress() + "@" + sockaddr.getPort();
    }

    void adjustWriteOp() {
        this.selector.checkThread();
        boolean interested = this.outbound.hasData();
        this.selector.adjustInterestOp(this.key, 4, interested);
    }

    static final SelectionKey bindAndConfigure(PyroSelector selector, SocketChannel channel, InetSocketAddress bind) throws IOException {
        selector.checkThread();
        channel.socket().bind(bind);
        return PyroClient.configure(selector, channel, true);
    }

    static final SelectionKey configure(PyroSelector selector, SocketChannel channel, boolean connect) throws IOException {
        selector.checkThread();
        channel.configureBlocking(false);
        channel.socket().setSoLinger(true, 4);
        channel.socket().setReuseAddress(false);
        channel.socket().setKeepAlive(false);
        channel.socket().setTcpNoDelay(true);
        channel.socket().setReceiveBufferSize(65536);
        channel.socket().setSendBufferSize(65536);
        int ops = 1;
        if (connect) {
            ops |= 8;
        }
        return selector.register(channel, ops);
    }

}

