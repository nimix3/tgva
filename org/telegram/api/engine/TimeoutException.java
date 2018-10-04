
package org.telegram.api.engine;

import java.io.IOException;

public class TimeoutException
extends IOException {
    public TimeoutException() {
    }

    public TimeoutException(String s) {
        super(s);
    }

    public TimeoutException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TimeoutException(Throwable throwable) {
        super(throwable);
    }
}

