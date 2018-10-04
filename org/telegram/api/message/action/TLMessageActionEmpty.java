
package org.telegram.api.message.action;

import org.telegram.api.message.action.TLAbsMessageAction;

public class TLMessageActionEmpty
extends TLAbsMessageAction {
    public static final int CLASS_ID = -1230047312;

    @Override
    public int getClassId() {
        return -1230047312;
    }

    @Override
    public String toString() {
        return "messageActionEmpty#b6aef7b0";
    }
}

