
package org.telegram.api.channel.participants;

import org.telegram.tl.TLObject;

public abstract class TLAbsChannelParticipant
extends TLObject {
    protected int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

