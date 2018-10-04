
package org.telegram.api.chat.participant.chatparticipant;

import org.telegram.tl.TLObject;

public abstract class TLAbsChatParticipant
extends TLObject {
    protected int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int value) {
        this.userId = value;
    }
}

