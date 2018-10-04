
package org.telegram.mtproto.secure.pq;

import java.math.BigInteger;
import org.telegram.mtproto.secure.pq.PQImplementation;
import org.telegram.mtproto.secure.pq.PQLopatin;

public class PQSolver {
    private static PQImplementation currentImplementation = new PQLopatin();

    private PQSolver() {
    }

    public static void setCurrentImplementation(PQImplementation implementation) {
        currentImplementation = implementation;
    }

    public static BigInteger solvePq(BigInteger src) {
        return new BigInteger("" + currentImplementation.findDivider(src.longValue()));
    }
}

