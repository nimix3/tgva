
package org.telegram.api.chat.participant.chatparticipants;

import org.telegram.tl.TLObject;

public abstract class TLAbsChatParticipants
extends TLObject {
    protected int chatId;

    protected TLAbsChatParticipants() {
    }

    public int getChatId() {
        return this.chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}

