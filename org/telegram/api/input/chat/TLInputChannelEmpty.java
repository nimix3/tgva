
package org.telegram.api.input.chat;

import org.telegram.api.input.chat.TLAbsInputChannel;

public class TLInputChannelEmpty
extends TLAbsInputChannel {
    public static final int CLASS_ID = -292807034;

    @Override
    public int getClassId() {
        return -292807034;
    }

    @Override
    public String toString() {
        return "input.chat.TLInputChannelEmpty#ee8c1e86";
    }

    @Override
    public int getChannelId() {
        return 0;
    }
}

