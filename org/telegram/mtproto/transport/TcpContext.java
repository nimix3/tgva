
package org.telegram.mtproto.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import jawnae.pyronet.PyroClient;
import jawnae.pyronet.PyroClientListener;
import jawnae.pyronet.PyroSelector;
import org.telegram.mtproto.MTProto;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.transport.BuffersStorage;
import org.telegram.mtproto.transport.ByteBufferDesc;
import org.telegram.mtproto.transport.ConnectionState;
import org.telegram.mtproto.transport.TcpContextCallback;

public class TcpContext
implements PyroClientListener {
    private static volatile Integer nextChannelToken = 1;
    private static final int MAX_PACKED_SIZE = 1073741824;
    private static final AtomicInteger contextLastId = new AtomicInteger(1);
    private static final int CONNECTION_TIMEOUT = 30000;
    private ConnectionState connectionState = ConnectionState.TcpConnectionStageIdle;
    private int failedConnectionCount;
    private int willRetryConnectCount = 5;
    private boolean hasSomeDataSinceLastConnect = false;
    private int channelToken = 0;
    private final Object timerSync = new Object();
    private Timer reconnectTimer;
    private boolean isFirstPackage = true;
    private final String TAG;
    private final String ip;
    private final int port;
    private final int contextId = contextLastId.incrementAndGet();
    private int sentPackets;
    private PyroSelector selector;
    private PyroClient client;
    private ByteBufferDesc restOfTheData;
    private int lastPacketLength;
    private TcpContextCallback callback;

    private static int generateChannelToken() {
        Integer n = nextChannelToken;
        Integer n2 = nextChannelToken = Integer.valueOf(nextChannelToken + 1);
        return n;
    }

    public TcpContext(MTProto proto, String ip, int port, TcpContextCallback callback) {
        this.TAG = "MTProto#" + proto.getInstanceIndex() + "#Transport" + this.contextId;
        this.ip = ip;
        this.port = port;
        this.callback = callback;
        this.selector = new PyroSelector();
        this.selector.spawnNetworkThread("Selector Thread");
        BuffersStorage.getInstance();
    }

    private void readData(ByteBuffer buffer) throws Exception {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.rewind();
        ByteBuffer parseLaterBuffer = null;
        if (this.restOfTheData != null) {
            if (this.lastPacketLength == 0) {
                if (this.restOfTheData.capacity() - this.restOfTheData.position() >= buffer.limit()) {
                    this.restOfTheData.limit(this.restOfTheData.position() + buffer.limit());
                    this.restOfTheData.put(buffer);
                    buffer = this.restOfTheData.buffer;
                } else {
                    ByteBufferDesc newBuffer = BuffersStorage.getInstance().getFreeBuffer(this.restOfTheData.limit() + buffer.limit());
                    this.restOfTheData.rewind();
                    newBuffer.put(this.restOfTheData.buffer);
                    newBuffer.put(buffer);
                    buffer = newBuffer.buffer;
                    BuffersStorage.getInstance().reuseFreeBuffer(this.restOfTheData);
                    this.restOfTheData = newBuffer;
                }
            } else {
                int len = this.lastPacketLength - this.restOfTheData.position() <= buffer.limit() ? this.lastPacketLength - this.restOfTheData.position() : buffer.limit();
                int oldLimit = buffer.limit();
                buffer.limit(len);
                this.restOfTheData.put(buffer);
                buffer.limit(oldLimit);
                if (this.restOfTheData.position() == this.lastPacketLength) {
                    parseLaterBuffer = buffer.hasRemaining() ? buffer : null;
                    buffer = this.restOfTheData.buffer;
                } else {
                    return;
                }
            }
        }
        buffer.rewind();
        while (buffer.hasRemaining()) {
            int currentPacketLength;
            if (!this.hasSomeDataSinceLastConnect) {
                this.client.setTimeout(900000);
            }
            this.hasSomeDataSinceLastConnect = true;
            buffer.mark();
            byte fByte = buffer.get();
            if ((fByte & 128) != 0) {
                buffer.reset();
                if (buffer.remaining() < 4) {
                    ByteBufferDesc reuseLater = this.restOfTheData;
                    this.restOfTheData = BuffersStorage.getInstance().getFreeBuffer(16384);
                    this.restOfTheData.put(buffer);
                    this.restOfTheData.limit(this.restOfTheData.position());
                    this.lastPacketLength = 0;
                    if (reuseLater == null) break;
                    BuffersStorage.getInstance().reuseFreeBuffer(reuseLater);
                    break;
                }
                buffer.order(ByteOrder.BIG_ENDIAN);
                int ackId = buffer.getInt() & Integer.MAX_VALUE;
                this.callback.onFastConfirm(ackId);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                continue;
            }
            if (fByte == 127) {
                buffer.reset();
                if (buffer.remaining() < 4) {
                    if (this.restOfTheData == null || this.restOfTheData != null && this.restOfTheData.position() != 0) {
                        ByteBufferDesc reuseLater = this.restOfTheData;
                        this.restOfTheData = BuffersStorage.getInstance().getFreeBuffer(16384);
                        this.restOfTheData.put(buffer);
                        this.restOfTheData.limit(this.restOfTheData.position());
                        this.lastPacketLength = 0;
                        if (reuseLater == null) break;
                        BuffersStorage.getInstance().reuseFreeBuffer(reuseLater);
                        break;
                    }
                    this.restOfTheData.position(this.restOfTheData.limit());
                    break;
                }
                currentPacketLength = (buffer.getInt() >> 8) * 4;
            } else {
                currentPacketLength = fByte * 4;
            }
            if (currentPacketLength % 4 != 0 || currentPacketLength > 2097152) {
                Logger.w(this.TAG, "Invalid packet length");
                this.reconnect();
                return;
            }
            if (currentPacketLength < buffer.remaining()) {
                Logger.d(this.TAG, this + " Received message len " + currentPacketLength + " but packet larger " + buffer.remaining());
            } else if (currentPacketLength == buffer.remaining()) {
                Logger.d(this.TAG, this + " Received message len " + currentPacketLength + " equal to packet size");
            } else {
                Logger.d(this.TAG, this + " Received packet size less(" + buffer.remaining() + ") then message size(" + currentPacketLength + ")");
                ByteBufferDesc reuseLater = null;
                int len = currentPacketLength + (fByte == 127 ? 4 : 1);
                if (this.restOfTheData != null && this.restOfTheData.capacity() < len) {
                    reuseLater = this.restOfTheData;
                    this.restOfTheData = null;
                }
                if (this.restOfTheData == null) {
                    buffer.reset();
                    this.restOfTheData = BuffersStorage.getInstance().getFreeBuffer(len);
                    this.restOfTheData.put(buffer);
                } else {
                    this.restOfTheData.position(this.restOfTheData.limit());
                    this.restOfTheData.limit(len);
                }
                this.lastPacketLength = len;
                if (reuseLater != null) {
                    BuffersStorage.getInstance().reuseFreeBuffer(reuseLater);
                }
                return;
            }
            ByteBufferDesc toProceed = BuffersStorage.getInstance().getFreeBuffer(currentPacketLength);
            int old = buffer.limit();
            buffer.limit(buffer.position() + currentPacketLength);
            toProceed.put(buffer);
            buffer.limit(old);
            toProceed.rewind();
            if (toProceed.buffer.remaining() == 4) {
                int error = toProceed.readInt32(false);
                this.onError(error);
            } else {
                byte[] pkg = new byte[toProceed.buffer.remaining()];
                toProceed.readRaw(pkg, false);
                this.onMessage(pkg, currentPacketLength);
                BuffersStorage.getInstance().reuseFreeBuffer(toProceed);
            }
            if (this.restOfTheData != null) {
                if (this.lastPacketLength != 0 && this.restOfTheData.position() == this.lastPacketLength || this.lastPacketLength == 0 && !this.restOfTheData.hasRemaining()) {
                    BuffersStorage.getInstance().reuseFreeBuffer(this.restOfTheData);
                    this.restOfTheData = null;
                } else {
                    this.restOfTheData.compact();
                    this.restOfTheData.limit(this.restOfTheData.position());
                    this.restOfTheData.position(0);
                }
            }
            if (parseLaterBuffer == null) continue;
            buffer = parseLaterBuffer;
            parseLaterBuffer = null;
        }
    }

    public int getContextId() {
        return this.contextId;
    }

    public void postMessage(byte[] data, boolean useFastConfirm) {
        ByteBufferDesc buffer = BuffersStorage.getInstance().getFreeBuffer(data.length);
        buffer.writeRaw(data);
        this.sendData(buffer, true, useFastConfirm);
    }

    private synchronized void onMessage(byte[] data, int len) {
        this.callback.onRawMessage(data, 0, len, this);
    }

    private synchronized void onError(int errorCode) {
        this.callback.onError(errorCode, this);
    }

    private void sendData(ByteBufferDesc buff, boolean canReuse, boolean reportAck) {
        if (buff == null) {
            return;
        }
        this.selector.scheduleTask(() -> {
            if (this.connectionState == ConnectionState.TcpConnectionStageIdle || this.connectionState == ConnectionState.TcpConnectionStageReconnecting || this.connectionState == ConnectionState.TcpConnectionStageSuspended || this.client == null) {
                this.connect();
            }
            if (this.client == null || this.client.isDisconnected()) {
                if (canReuse) {
                    BuffersStorage.getInstance().reuseFreeBuffer(buff);
                }
                Logger.e(this.TAG, this + " disconnected, don't send data");
                return;
            }
            int bufferLen = buff.limit();
            int packetLength = bufferLen / 4;
            bufferLen = packetLength < 127 ? ++bufferLen : (bufferLen += 4);
            if (this.isFirstPackage) {
                ++bufferLen;
            }
            ByteBufferDesc buffer = BuffersStorage.getInstance().getFreeBuffer(bufferLen);
            if (this.isFirstPackage) {
                buffer.writeByte((byte)-17);
                this.isFirstPackage = false;
            }
            if (packetLength < 127) {
                if (reportAck) {
                    packetLength |= 128;
                }
                buffer.writeByte(packetLength);
            } else {
                packetLength = (packetLength << 8) + 127;
                if (reportAck) {
                    packetLength |= 128;
                }
                buffer.writeInt32(packetLength);
            }
            buffer.writeRaw(buff);
            if (canReuse) {
                BuffersStorage.getInstance().reuseFreeBuffer(buff);
            }
            buffer.rewind();
            ++this.sentPackets;
            this.client.write(buffer);
        });
    }

    @Override
    public void connectedClient(PyroClient client) {
        this.connectionState = ConnectionState.TcpConnectionStageConnected;
        this.channelToken = TcpContext.generateChannelToken();
        Logger.d(this.TAG, "Client connected: " + this.channelToken);
    }

    @Override
    public void unconnectableClient(PyroClient client, Exception cause) {
        this.handleDisconnect(client, false);
    }

    @Override
    public void droppedClient(PyroClient client, IOException cause) {
        this.handleDisconnect(client, cause instanceof SocketTimeoutException);
    }

    @Override
    public void disconnectedClient(PyroClient client) {
        this.handleDisconnect(client, false);
    }

    @Override
    public void receivedData(PyroClient client, ByteBuffer data) {
        try {
            this.failedConnectionCount = 0;
            this.readData(data);
        }
        catch (Exception e) {
            Logger.e(this.TAG, e);
            this.reconnect();
        }
    }

    @Override
    public void sentData(PyroClient client, int bytes) {
        Logger.d(this.TAG, "Received data " + bytes);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void handleDisconnect(PyroClient client, boolean timeout) {
        Object object = this.timerSync;
        synchronized (object) {
            if (this.reconnectTimer != null) {
                this.reconnectTimer.cancel();
                this.reconnectTimer = null;
            }
        }
        this.isFirstPackage = true;
        if (this.restOfTheData != null) {
            BuffersStorage.getInstance().reuseFreeBuffer(this.restOfTheData);
            this.restOfTheData = null;
        }
        this.channelToken = 0;
        this.lastPacketLength = 0;
        if (this.connectionState != ConnectionState.TcpConnectionStageSuspended && this.connectionState != ConnectionState.TcpConnectionStageIdle) {
            this.connectionState = ConnectionState.TcpConnectionStageIdle;
        }
        this.callback.onChannelBroken(this);
        if (this.connectionState == ConnectionState.TcpConnectionStageIdle) {
            ++this.failedConnectionCount;
            if (this.failedConnectionCount == 1) {
                this.willRetryConnectCount = this.hasSomeDataSinceLastConnect ? 5 : 1;
            }
            Logger.d(this.TAG, "Reconnect " + this.ip + ":" + this.port + " " + this);
            try {
                object = this.timerSync;
                synchronized (object) {
                    this.reconnectTimer = new Timer();
                    this.reconnectTimer.schedule(new TimerTask(){

                        @Override
                        public void run() {
                            TcpContext.this.selector.scheduleTask(() -> {
                                try {
                                    Object object = TcpContext.this.timerSync;
                                    synchronized (object) {
                                        if (TcpContext.this.reconnectTimer != null) {
                                            TcpContext.this.reconnectTimer.cancel();
                                            TcpContext.this.reconnectTimer = null;
                                        }
                                    }
                                }
                                catch (Exception e2) {
                                    Logger.e(TcpContext.this.TAG, e2);
                                }
                                TcpContext.this.connect();
                            });
                        }
                    }, this.failedConnectionCount > 3 ? 500L : 300L, this.failedConnectionCount > 3 ? 500L : 300L);
                }
            }
            catch (Exception e3) {
                Logger.e(this.TAG, e3);
            }
        }
    }

    public void connect() {
        this.selector.scheduleTask(() -> {
            if ((this.connectionState == ConnectionState.TcpConnectionStageConnected || this.connectionState == ConnectionState.TcpConnectionStageConnecting) && this.client != null) {
                return;
            }
            this.connectionState = ConnectionState.TcpConnectionStageConnecting;
            try {
                try {
                    Object object = this.timerSync;
                    synchronized (object) {
                        if (this.reconnectTimer != null) {
                            this.reconnectTimer.cancel();
                            this.reconnectTimer = null;
                        }
                    }
                }
                catch (Exception e2) {
                    Logger.e(this.TAG, e2);
                }
                Logger.d(this.TAG, String.format(this + " Connecting (%s:%d)", this.ip, this.port));
                this.isFirstPackage = true;
                if (this.restOfTheData != null) {
                    BuffersStorage.getInstance().reuseFreeBuffer(this.restOfTheData);
                    this.restOfTheData = null;
                }
                this.lastPacketLength = 0;
                this.hasSomeDataSinceLastConnect = false;
                if (this.client != null) {
                    this.client.removeListener(this);
                    this.client.dropConnection();
                    this.client = null;
                }
                this.client = this.selector.connect(new InetSocketAddress(this.ip, this.port));
                this.client.addListener(this);
                this.client.setTimeout(30000);
                this.selector.wakeup();
            }
            catch (Exception e) {
                this.handleConnectionError(e);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleConnectionError(Exception e) {
        try {
            Object object = this.timerSync;
            synchronized (object) {
                if (this.reconnectTimer != null) {
                    this.reconnectTimer.cancel();
                    this.reconnectTimer = null;
                }
            }
        }
        catch (Exception e2) {
            Logger.e(this.TAG, e2);
        }
        this.connectionState = ConnectionState.TcpConnectionStageReconnecting;
        this.callback.onChannelBroken(this);
        ++this.failedConnectionCount;
        if (this.failedConnectionCount == 1) {
            this.willRetryConnectCount = this.hasSomeDataSinceLastConnect ? 3 : 1;
        }
        if (this.failedConnectionCount > this.willRetryConnectCount) {
            this.failedConnectionCount = 0;
        }
        if (e != null) {
            Logger.e(this.TAG, e);
        }
        Logger.d(this.TAG, "Reconnect " + this.ip + ":" + this.port + " " + this);
        try {
            this.reconnectTimer = new Timer();
            this.reconnectTimer.schedule(new TimerTask(){

                @Override
                public void run() {
                    TcpContext.this.selector.scheduleTask(new Runnable(){

                        /*
                         * WARNING - Removed try catching itself - possible behaviour change.
                         */
                        @Override
                        public void run() {
                            try {
                                Object object = TcpContext.this.timerSync;
                                synchronized (object) {
                                    if (TcpContext.this.reconnectTimer != null) {
                                        TcpContext.this.reconnectTimer.cancel();
                                        TcpContext.this.reconnectTimer = null;
                                    }
                                }
                            }
                            catch (Exception e2) {
                                Logger.e(TcpContext.this.TAG, e2);
                            }
                            TcpContext.this.connect();
                        }
                    });
                }

            }, this.failedConnectionCount >= 3 ? 500L : 300L, this.failedConnectionCount >= 3 ? 500L : 300L);
        }
        catch (Exception e3) {
            Logger.e(this.TAG, e3);
        }
    }

    private void reconnect() {
        this.suspendConnection(false);
        this.connectionState = ConnectionState.TcpConnectionStageReconnecting;
        this.connect();
    }

    public void suspendConnection(boolean task) {
        if (task) {
            this.selector.scheduleTask(new Runnable(){

                @Override
                public void run() {
                    TcpContext.this.suspendConnectionInternal();
                }
            });
        } else {
            this.suspendConnectionInternal();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void suspendConnectionInternal() {
        Object object = this.timerSync;
        synchronized (object) {
            if (this.reconnectTimer != null) {
                this.reconnectTimer.cancel();
                this.reconnectTimer = null;
            }
        }
        if (this.connectionState == ConnectionState.TcpConnectionStageIdle || this.connectionState == ConnectionState.TcpConnectionStageSuspended) {
            return;
        }
        Logger.d(this.TAG, "suspend connnection " + this);
        this.connectionState = ConnectionState.TcpConnectionStageSuspended;
        if (this.client != null) {
            this.client.removeListener(this);
            this.client.dropConnection();
            this.client = null;
        }
        this.callback.onChannelBroken(this);
        this.isFirstPackage = true;
        if (this.restOfTheData != null) {
            BuffersStorage.getInstance().reuseFreeBuffer(this.restOfTheData);
            this.restOfTheData = null;
        }
        this.lastPacketLength = 0;
        this.channelToken = 0;
    }

}

