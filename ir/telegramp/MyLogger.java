
package ir.telegramp;

import ir.telegramp.JarPath;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyLogger {
    public static String log_path = JarPath.GetPath() + "//";
    private static String file_name = "debug";
    public static BufferedWriter bufferWritter = null;
    public static boolean file_logging = true;

    public static void log(String message, int index) {
        MyLogger.log(message, "", true, index, true);
    }

    public static void log(String message, String extra, boolean newLine, int index, boolean time) {
        try {
            extra = "{" + new SimpleDateFormat("MM/dd|HH:mm:ss").format(Calendar.getInstance().getTime()) + "}=> " + extra + ": ";
            System.out.println(extra + message);
            if (!file_logging) {
                return;
            }
            String str = log_path + file_name + (index <= 0 ? "" : Integer.valueOf(index)) + ".log";
            File file = new File(str);
            if (!file.exists()) {
                new File(log_path).mkdirs();
                file.createNewFile();
            }
            Files.write(file.toPath(), (extra + message + (newLine ? "\r\n" : "")).getBytes("utf-8"), StandardOpenOption.APPEND);
        }
        catch (Exception str) {
            // empty catch block
        }
    }

    public static void log(int message) {
        MyLogger.log("" + message + "", "", true, 0, true);
    }

    public static void log(int message, String extra) {
        MyLogger.log("" + message + "", extra, true, 0, true);
    }

    public static void log(String message, int index, boolean newLine) {
        MyLogger.log(message, "", newLine, index, true);
    }

    public static void log(String message, int index, boolean newLine, boolean time) {
        MyLogger.log(message, "", time, index, newLine);
    }

    public static void log(String message, String extra) {
        MyLogger.log(message, extra, true, 0, true);
    }

    public static void log(String message, String extra, int index) {
        MyLogger.log(message, extra, true, index, true);
    }
}

