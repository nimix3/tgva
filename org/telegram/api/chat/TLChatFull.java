
package org.telegram.api.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.bot.TLBotInfo;
import org.telegram.api.chat.TLAbsChatFull;
import org.telegram.api.chat.invite.TLAbsChatInvite;
import org.telegram.api.chat.invite.TLChatInviteEmpty;
import org.telegram.api.chat.participant.chatparticipants.TLAbsChatParticipants;
import org.telegram.api.peer.notify.settings.TLAbsPeerNotifySettings;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLVector;

public class TLChatFull
extends TLAbsChatFull {
    public static final int CLASS_ID = 771925524;
    private TLAbsChatParticipants participants;
    private TLVector<TLBotInfo> botInfo = new TLVector();

    @Override
    public int getClassId() {
        return 771925524;
    }

    public TLAbsChatParticipants getParticipants() {
        return this.participants;
    }

    public void setParticipants(TLAbsChatParticipants value) {
        this.participants = value;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLObject(this.participants, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeTLObject(this.notifySettings, stream);
        StreamingUtils.writeTLObject(this.exportedInvite, stream);
        StreamingUtils.writeTLVector(this.botInfo, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.participants = (TLAbsChatParticipants)StreamingUtils.readTLObject(stream, context);
        this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        this.notifySettings = (TLAbsPeerNotifySettings)StreamingUtils.readTLObject(stream, context);
        this.exportedInvite = (TLChatInviteEmpty)StreamingUtils.readTLObject(stream, context);
        this.botInfo = StreamingUtils.readTLVector(stream, context);
    }

    @Override
    public String toString() {
        return "chatFull#2e02a614";
    }
}

