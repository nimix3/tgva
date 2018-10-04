
package org.telegram.mtproto.state;

import java.util.Collection;
import java.util.HashMap;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.state.KnownSalt;
import org.telegram.mtproto.time.TimeOverlord;

public abstract class AbsMTProtoState {
    public abstract byte[] getAuthKey();

    public abstract ConnectionInfo[] getAvailableConnections();

    public abstract KnownSalt[] readKnownSalts();

    protected abstract void writeKnownSalts(KnownSalt[] var1);

    public void mergeKnownSalts(int currentTime, KnownSalt[] salts) {
        KnownSalt[] knownSalts = this.readKnownSalts();
        HashMap<Long, KnownSalt> ids = new HashMap<Long, KnownSalt>();
        for (KnownSalt s : knownSalts) {
            if (s.getValidUntil() < currentTime) continue;
            ids.put(s.getSalt(), s);
        }
        for (KnownSalt s : salts) {
            if (s.getValidUntil() < currentTime) continue;
            ids.put(s.getSalt(), s);
        }
        this.writeKnownSalts(ids.values().toArray(new KnownSalt[0]));
    }

    public void addCurrentSalt(long salt) {
        int time = (int)(TimeOverlord.getInstance().getServerTime() / 1000L);
        this.mergeKnownSalts(time, new KnownSalt[]{new KnownSalt(time, time + 1800, salt)});
    }

    public void badServerSalt(long salt) {
        int time = (int)(TimeOverlord.getInstance().getServerTime() / 1000L);
        this.writeKnownSalts(new KnownSalt[]{new KnownSalt(time, time + 1800, salt)});
    }

    public void initialServerSalt(long salt) {
        int time = (int)(TimeOverlord.getInstance().getServerTime() / 1000L);
        this.writeKnownSalts(new KnownSalt[]{new KnownSalt(time, time + 1800, salt)});
    }

    public long findActualSalt(int time) {
        KnownSalt[] knownSalts;
        for (KnownSalt salt : knownSalts = this.readKnownSalts()) {
            if (salt.getValidSince() > time || time > salt.getValidUntil()) continue;
            return salt.getSalt();
        }
        return 0L;
    }

    public int maximumCachedSalts(int time) {
        int count = 0;
        for (KnownSalt salt : this.readKnownSalts()) {
            if (salt.getValidSince() <= time) continue;
            ++count;
        }
        return count;
    }

    public int maximumCachedSaltsTime() {
        int max = 0;
        for (KnownSalt salt : this.readKnownSalts()) {
            max = Math.max(max, salt.getValidUntil());
        }
        return max;
    }
}

