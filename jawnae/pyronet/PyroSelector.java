
package jawnae.pyronet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import jawnae.pyronet.PyroClient;
import jawnae.pyronet.PyroException;

public class PyroSelector {
    private static boolean DO_NOT_CHECK_NETWORK_THREAD = false;
    static final int BUFFER_SIZE = 65536;
    private Thread networkThread;
    private final Selector nioSelector;
    final ByteBuffer networkBuffer = ByteBuffer.allocateDirect(65536);
    private BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<Runnable>();

    public PyroSelector() {
        try {
            this.nioSelector = Selector.open();
        }
        catch (IOException exc) {
            throw new PyroException("Failed to open a selector?!", exc);
        }
        this.networkThread = Thread.currentThread();
    }

    final boolean isNetworkThread() {
        return DO_NOT_CHECK_NETWORK_THREAD || this.networkThread == Thread.currentThread();
    }

    public final Thread networkThread() {
        return this.networkThread;
    }

    public final void checkThread() {
        if (DO_NOT_CHECK_NETWORK_THREAD) {
            return;
        }
        if (!this.isNetworkThread()) {
            throw new PyroException("call from outside the network-thread, you must schedule tasks");
        }
    }

    public PyroClient connect(InetSocketAddress host) throws IOException {
        return this.connect(host, null);
    }

    public PyroClient connect(InetSocketAddress host, InetSocketAddress bind) throws IOException {
        return new PyroClient(this, bind, host);
    }

    public void select() {
        this.select(0L);
    }

    public void select(long eventTimeout) {
        this.checkThread();
        this.executePendingTasks();
        this.performNioSelect(eventTimeout);
        long now = System.currentTimeMillis();
        this.handleSelectedKeys(now);
        this.handleSocketTimeouts(now);
    }

    private void executePendingTasks() {
        Runnable task;
        while ((task = this.tasks.poll()) != null) {
            try {
                task.run();
            }
            catch (Throwable cause) {
                cause.printStackTrace();
            }
        }
    }

    private final void performNioSelect(long timeout) {
        try {
            int selected = this.nioSelector.select(timeout);
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private final void handleSelectedKeys(long now) {
        Iterator<SelectionKey> keys = this.nioSelector.selectedKeys().iterator();
        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            keys.remove();
            if (!(key.channel() instanceof SocketChannel)) continue;
            PyroClient client = (PyroClient)key.attachment();
            client.onInterestOp(now);
        }
    }

    private final void handleSocketTimeouts(long now) {
        for (SelectionKey key : this.nioSelector.keys()) {
            PyroClient client;
            if (!(key.channel() instanceof SocketChannel) || !(client = (PyroClient)key.attachment()).didTimeout(now)) continue;
            try {
                throw new SocketTimeoutException("PyroNet detected NIO timeout");
            }
            catch (SocketTimeoutException exc) {
                client.onConnectionError(exc);
            }
        }
    }

    public void spawnNetworkThread(String name) {
        this.networkThread = null;
        new Thread(new Runnable(){

            @Override
            public void run() {
                PyroSelector.this.networkThread = Thread.currentThread();
                try {
                    do {
                        PyroSelector.this.select();
                    } while (true);
                }
                catch (Exception exc) {
                    throw new IllegalStateException(exc);
                }
            }
        }, name).start();
    }

    public void scheduleTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }
        try {
            this.tasks.put(task);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.wakeup();
    }

    public void wakeup() {
        this.nioSelector.wakeup();
    }

    public void close() throws IOException {
        this.networkThread.interrupt();
        this.nioSelector.close();
        this.networkBuffer.clear();
    }

    final SelectionKey register(SelectableChannel channel, int ops) throws IOException {
        return channel.register(this.nioSelector, ops);
    }

    final boolean adjustInterestOp(SelectionKey key, int op, boolean state) {
        this.checkThread();
        try {
            boolean changed;
            int ops = key.interestOps();
            boolean bl = changed = state != ((ops & op) == op);
            if (changed) {
                key.interestOps(state ? ops | op : ops & ~ op);
            }
            return changed;
        }
        catch (CancelledKeyException exc) {
            return false;
        }
    }

}

