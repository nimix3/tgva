
package org.telegram.mtproto.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.WeakHashMap;

public class BytesCache {
    private static BytesCache instance = new BytesCache("GlobalByteCache");
    private final int[] SIZES = new int[]{64, 128, 3072, 20480, 40960};
    private final int MAX_SIZE = 40960;
    private final boolean TRACK_ALLOCATIONS = false;
    private final String TAG;
    private HashMap<Integer, HashSet<byte[]>> fastBuffers = new HashMap();
    private HashSet<byte[]> mainFilter = new HashSet();
    private HashSet<byte[]> byteBuffer = new HashSet();
    private WeakHashMap<byte[], StackTraceElement[]> references = new WeakHashMap();

    public BytesCache(String logTag) {
        this.TAG = logTag;
        for (int i = 0; i < this.SIZES.length; ++i) {
            this.fastBuffers.put(this.SIZES[i], new HashSet());
        }
    }

    public static BytesCache getInstance() {
        return instance;
    }

    public synchronized void put(byte[] data) {
        this.references.remove(data);
        if (this.mainFilter.add(data)) {
            int[] arrn = this.SIZES;
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                Integer i2 = arrn[i];
                if (data.length != i2) continue;
                this.fastBuffers.get(i2).add(data);
                return;
            }
            this.getClass();
            if (data.length <= 40960) {
                return;
            }
            this.byteBuffer.add(data);
        }
    }

    public synchronized byte[] allocate(int minSize) {
        this.getClass();
        if (minSize <= 40960) {
            for (int i = 0; i < this.SIZES.length; ++i) {
                if (minSize >= this.SIZES[i]) continue;
                if (!this.fastBuffers.get(this.SIZES[i]).isEmpty()) {
                    Iterator<byte[]> interator = this.fastBuffers.get(this.SIZES[i]).iterator();
                    byte[] res = interator.next();
                    interator.remove();
                    this.mainFilter.remove(res);
                    this.getClass();
                    return res;
                }
                return new byte[this.SIZES[i]];
            }
        } else {
            byte[] res = null;
            for (byte[] cached : this.byteBuffer) {
                if (cached.length < minSize) continue;
                if (res == null) {
                    res = cached;
                    continue;
                }
                if (res.length <= cached.length) continue;
                res = cached;
            }
            if (res != null) {
                this.byteBuffer.remove(res);
                this.mainFilter.remove(res);
                this.getClass();
                return res;
            }
        }
        return new byte[minSize];
    }
}

