
package org.telegram.mtproto.util;

public class TimeUtil {
    public static int getUnixTime(long msgId) {
        return (int)(msgId >> 32);
    }
}

