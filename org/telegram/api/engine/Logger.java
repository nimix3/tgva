
package org.telegram.api.engine;

import java.io.PrintStream;
import org.telegram.api.engine.LoggerInterface;

public class Logger {
    private static LoggerInterface logInterface;

    public static void registerInterface(LoggerInterface logInterface) {
        Logger.logInterface = logInterface;
    }

    public static void w(String tag, String message) {
        if (logInterface != null) {
            logInterface.w(tag, message);
        } else {
            System.out.println(tag + ":" + message);
        }
    }

    public static void d(String tag, String message) {
        if (logInterface != null) {
            logInterface.d(tag, message);
        } else {
            System.out.println(tag + ":" + message);
        }
    }

    public static void e(String tag, Throwable t) {
        if (logInterface != null) {
            logInterface.e(tag, t);
        } else {
            t.printStackTrace();
        }
    }
}

