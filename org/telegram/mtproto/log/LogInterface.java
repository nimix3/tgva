
package org.telegram.mtproto.log;

public interface LogInterface {
    public void w(String var1, String var2);

    public void d(String var1, String var2);

    public void e(String var1, String var2);

    public void e(String var1, Throwable var2);
}

