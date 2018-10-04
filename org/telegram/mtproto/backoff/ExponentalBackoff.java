
package org.telegram.mtproto.backoff;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.mtproto.log.Logger;

public class ExponentalBackoff {
    private static final int MIN_DELAY = 100;
    private static final int MAX_DELAY = 15000;
    private static final int MAX_FAILURE_COUNT = 50;
    private final String TAG;
    private Random rnd = new Random();
    private AtomicInteger currentFailureCount = new AtomicInteger();

    public ExponentalBackoff(String logTag) {
        this.TAG = logTag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onFailure() throws InterruptedException {
        int val = this.currentFailureCount.incrementAndGet();
        if (val > 50) {
            this.currentFailureCount.compareAndSet(val, 50);
            val = 50;
        }
        int delay = 100 + 298 * val;
        ExponentalBackoff exponentalBackoff = this;
        synchronized (exponentalBackoff) {
            Logger.d(this.TAG, "onFailure: wait " + delay + " ms");
            this.wait(delay);
        }
    }

    public void onFailureNoWait() {
        Logger.d(this.TAG, "onFailureNoWait");
        int val = this.currentFailureCount.incrementAndGet();
        if (val > 50) {
            this.currentFailureCount.compareAndSet(val, 50);
            val = 50;
        }
    }

    public void onSuccess() {
        Logger.d(this.TAG, "onSuccess");
        this.reset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset() {
        Logger.d(this.TAG, "reset");
        this.currentFailureCount.set(0);
        ExponentalBackoff exponentalBackoff = this;
        synchronized (exponentalBackoff) {
            this.notifyAll();
        }
    }
}

