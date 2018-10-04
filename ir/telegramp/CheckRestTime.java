
package ir.telegramp;

import java.util.Calendar;
import java.util.Date;

public class CheckRestTime {
    public static void Check() throws InterruptedException {
        long Now;
        long End;
        long Start;
        boolean RestTime = false;
        do {
            if (RestTime) {
                Thread.sleep(60000L);
            }
            Calendar cal = Calendar.getInstance();
            Now = cal.getTime().getTime();
            cal.set(11, 9);
            cal.set(12, 30);
            cal.set(13, 0);
            Start = cal.getTime().getTime();
            cal.set(11, 23);
            cal.set(12, 0);
            cal.set(13, 0);
            End = cal.getTime().getTime();
        } while (RestTime = Now - Start < 0L || Now - End > 0L);
    }
}

