
package org.telegram.mtproto.secure.pq;

import java.util.Random;
import org.telegram.mtproto.secure.pq.PQImplementation;

public class PQLopatin
implements PQImplementation {
    @Override
    public long findDivider(long src) {
        return this.findSmallMultiplierLopatin(src);
    }

    private long GCD(long a, long b) {
        while (a != 0L && b != 0L) {
            while ((b & 1L) == 0L) {
                b >>= 1;
            }
            while ((a & 1L) == 0L) {
                a >>= 1;
            }
            if (a > b) {
                a -= b;
                continue;
            }
            b -= a;
        }
        return b == 0L ? a : b;
    }

    private long findSmallMultiplierLopatin(long what) {
        Random r = new Random();
        long g = 0L;
        int it = 0;
        for (int i = 0; i < 3; ++i) {
            long x;
            int q = (r.nextInt(128) & 15) + 17;
            long y = x = (long)(r.nextInt(1000000000) + 1);
            int lim = 1 << i + 18;
            for (int j = 1; j < lim; ++j) {
                ++it;
                long a = x;
                long c = q;
                for (long b = x; b != 0L; b >>= 1) {
                    if ((b & 1L) != 0L && (c += a) >= what) {
                        c -= what;
                    }
                    if ((a += a) < what) continue;
                    a -= what;
                }
                x = c;
                long z = x < y ? y - x : x - y;
                g = this.GCD(z, what);
                if (g != 1L) break;
                if ((j & j - 1) != 0) continue;
                y = x;
            }
            if (g > 1L) break;
        }
        long p = what / g;
        return Math.min(p, g);
    }
}

