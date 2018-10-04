
package org.telegram.mtproto.time;

public class TimeOverlord {
    private static TimeOverlord instance;
    protected long timeDelta;
    private long nanotimeShift = System.currentTimeMillis() - System.nanoTime() / 1000L;
    private long timeAccuracy = Long.MAX_VALUE;

    private TimeOverlord() {
    }

    public static synchronized TimeOverlord getInstance() {
        if (instance == null) {
            instance = new TimeOverlord();
        }
        return instance;
    }

    public long createWeakMessageId() {
        return this.getServerTime() / 1000L << 32;
    }

    public long getLocalTime() {
        return System.currentTimeMillis();
    }

    public long getServerTime() {
        return this.getLocalTime() + this.timeDelta;
    }

    public long getTimeAccuracy() {
        return this.timeAccuracy;
    }

    public long getTimeDelta() {
        return this.timeDelta;
    }

    public void setTimeDelta(long timeDelta, long timeAccuracy) {
        this.timeDelta = timeDelta;
        this.timeAccuracy = timeAccuracy;
    }

    public void onForcedServerTimeArrived(long serverTime, long duration) {
        this.timeDelta = serverTime - this.getLocalTime();
        this.timeAccuracy = duration;
    }

    public void onServerTimeArrived(long serverTime, long duration) {
        if (duration < 0L) {
            return;
        }
        if (duration < this.timeAccuracy) {
            this.timeDelta = serverTime - this.getLocalTime();
            this.timeAccuracy = duration;
        } else if (Math.abs(this.getLocalTime() - serverTime) > duration / 2L + this.timeAccuracy / 2L) {
            this.timeDelta = serverTime - this.getLocalTime();
            this.timeAccuracy = duration;
        }
    }

    public void onMethodExecuted(long sentId, long responseId, long duration) {
        if (duration < 0L) {
            return;
        }
        this.onServerTimeArrived((responseId >> 32) * 1000L, duration);
    }
}

