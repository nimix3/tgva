
package org.telegram.mtproto.transport;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.transport.ByteBufferDesc;

public class BuffersStorage {
    private final ArrayList<ByteBufferDesc> freeBuffers128;
    private final ArrayList<ByteBufferDesc> freeBuffers1024;
    private final ArrayList<ByteBufferDesc> freeBuffers4096;
    private final ArrayList<ByteBufferDesc> freeBuffers16384;
    private final ArrayList<ByteBufferDesc> freeBuffers32768;
    private final ArrayList<ByteBufferDesc> freeBuffersBig;
    private boolean isThreadSafe;
    private static final Object sync = new Object();
    private static volatile BuffersStorage Instance = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static BuffersStorage getInstance() {
        BuffersStorage localInstance = Instance;
        if (localInstance != null) return localInstance;
        Class<BuffersStorage> class_ = BuffersStorage.class;
        synchronized (BuffersStorage.class) {
            localInstance = Instance;
            if (localInstance != null) return localInstance;
            {
                Instance = localInstance = new BuffersStorage(true);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return localInstance;
        }
    }

    public BuffersStorage(boolean threadSafe) {
        this.isThreadSafe = threadSafe;
        this.freeBuffers128 = new ArrayList();
        this.freeBuffers1024 = new ArrayList();
        this.freeBuffers4096 = new ArrayList();
        this.freeBuffers16384 = new ArrayList();
        this.freeBuffers32768 = new ArrayList();
        this.freeBuffersBig = new ArrayList();
        for (int a = 0; a < 5; ++a) {
            this.freeBuffers128.add(new ByteBufferDesc(128));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ByteBufferDesc getFreeBuffer(int size) {
        if (size <= 0) {
            return null;
        }
        int byteCount = 0;
        ArrayList<ByteBufferDesc> arrayToGetFrom = null;
        ByteBufferDesc buffer = null;
        if (size <= 128) {
            arrayToGetFrom = this.freeBuffers128;
            byteCount = 128;
        } else if (size <= 1224) {
            arrayToGetFrom = this.freeBuffers1024;
            byteCount = 1224;
        } else if (size <= 4296) {
            arrayToGetFrom = this.freeBuffers4096;
            byteCount = 4296;
        } else if (size <= 16584) {
            arrayToGetFrom = this.freeBuffers16384;
            byteCount = 16584;
        } else if (size <= 40000) {
            arrayToGetFrom = this.freeBuffers32768;
            byteCount = 40000;
        } else if (size <= 280000) {
            arrayToGetFrom = this.freeBuffersBig;
            byteCount = 280000;
        } else {
            buffer = new ByteBufferDesc(size);
        }
        if (arrayToGetFrom != null) {
            if (this.isThreadSafe) {
                Object object = sync;
                synchronized (object) {
                    if (arrayToGetFrom.size() > 0) {
                        buffer = arrayToGetFrom.get(0);
                        arrayToGetFrom.remove(0);
                    }
                }
            } else if (arrayToGetFrom.size() > 0) {
                buffer = arrayToGetFrom.get(0);
                arrayToGetFrom.remove(0);
            }
            if (buffer == null) {
                buffer = new ByteBufferDesc(byteCount);
                Logger.w("tmessages", "create new " + byteCount + " buffer");
            }
        }
        buffer.buffer.limit(size).rewind();
        return buffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reuseFreeBuffer(ByteBufferDesc buffer) {
        if (buffer == null) {
            return;
        }
        int maxCount = 10;
        ArrayList<ByteBufferDesc> arrayToReuse = null;
        if (buffer.buffer.capacity() == 128) {
            arrayToReuse = this.freeBuffers128;
        } else if (buffer.buffer.capacity() == 1224) {
            arrayToReuse = this.freeBuffers1024;
        }
        if (buffer.buffer.capacity() == 4296) {
            arrayToReuse = this.freeBuffers4096;
        } else if (buffer.buffer.capacity() == 16584) {
            arrayToReuse = this.freeBuffers16384;
        } else if (buffer.buffer.capacity() == 40000) {
            arrayToReuse = this.freeBuffers32768;
        } else if (buffer.buffer.capacity() == 280000) {
            arrayToReuse = this.freeBuffersBig;
            maxCount = 10;
        }
        if (arrayToReuse != null) {
            if (this.isThreadSafe) {
                Object object = sync;
                synchronized (object) {
                    if (arrayToReuse.size() < maxCount) {
                        arrayToReuse.add(buffer);
                    } else {
                        Logger.w("tmessages", "too more");
                    }
                }
            } else if (arrayToReuse.size() < maxCount) {
                arrayToReuse.add(buffer);
            }
        }
    }
}

