
package ir.telegramp;

import ir.telegramp.IUpdatesHandler;
import ir.telegramp.UpdateWrapper;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.TLMessage;
import org.telegram.api.message.TLMessageFwdHeader;
import org.telegram.api.message.TLMessageService;
import org.telegram.api.notify.peer.TLAbsNotifyPeer;
import org.telegram.api.notify.peer.TLNotifyPeer;
import org.telegram.api.peer.TLAbsPeer;
import org.telegram.api.peer.TLPeerUser;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.update.TLFakeUpdate;
import org.telegram.api.update.TLUpdateBotCallbackQuery;
import org.telegram.api.update.TLUpdateBotInlineQuery;
import org.telegram.api.update.TLUpdateBotInlineSend;
import org.telegram.api.update.TLUpdateChannel;
import org.telegram.api.update.TLUpdateChannelGroup;
import org.telegram.api.update.TLUpdateChannelMessageViews;
import org.telegram.api.update.TLUpdateChannelNewMessage;
import org.telegram.api.update.TLUpdateChannelPinnedMessage;
import org.telegram.api.update.TLUpdateChannelTooLong;
import org.telegram.api.update.TLUpdateChatAdmin;
import org.telegram.api.update.TLUpdateChatParticipantAdd;
import org.telegram.api.update.TLUpdateChatParticipantAdmin;
import org.telegram.api.update.TLUpdateChatParticipantDelete;
import org.telegram.api.update.TLUpdateChatParticipants;
import org.telegram.api.update.TLUpdateChatUserTyping;
import org.telegram.api.update.TLUpdateContactLink;
import org.telegram.api.update.TLUpdateContactRegistered;
import org.telegram.api.update.TLUpdateDcOptions;
import org.telegram.api.update.TLUpdateDeleteChannelMessages;
import org.telegram.api.update.TLUpdateDeleteMessages;
import org.telegram.api.update.TLUpdateEditChannelMessage;
import org.telegram.api.update.TLUpdateEditMessage;
import org.telegram.api.update.TLUpdateInlineBotCallbackQuery;
import org.telegram.api.update.TLUpdateMessageId;
import org.telegram.api.update.TLUpdateNewAuthorization;
import org.telegram.api.update.TLUpdateNewMessage;
import org.telegram.api.update.TLUpdateNewStickerSet;
import org.telegram.api.update.TLUpdateNotifySettings;
import org.telegram.api.update.TLUpdatePrivacy;
import org.telegram.api.update.TLUpdateReadChannelInbox;
import org.telegram.api.update.TLUpdateReadMessagesContents;
import org.telegram.api.update.TLUpdateReadMessagesInbox;
import org.telegram.api.update.TLUpdateReadMessagesOutbox;
import org.telegram.api.update.TLUpdateSavedGifs;
import org.telegram.api.update.TLUpdateServiceNotification;
import org.telegram.api.update.TLUpdateStickerSets;
import org.telegram.api.update.TLUpdateStickerSetsOrder;
import org.telegram.api.update.TLUpdateUserBlocked;
import org.telegram.api.update.TLUpdateUserName;
import org.telegram.api.update.TLUpdateUserPhone;
import org.telegram.api.update.TLUpdateUserPhoto;
import org.telegram.api.update.TLUpdateUserStatus;
import org.telegram.api.update.TLUpdateUserTyping;
import org.telegram.api.update.TLUpdateWebPage;
import org.telegram.api.updates.TLUpdateShortChatMessage;
import org.telegram.api.updates.TLUpdateShortMessage;
import org.telegram.api.updates.TLUpdateShortSentMessage;
import org.telegram.api.updates.TLUpdatesState;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class UpdatesHandlerBase
implements IUpdatesHandler {
    private static final String LOGTAG = "UPDATESHANDLERBASE";

    @Override
    public final void processUpdate(UpdateWrapper updateWrapper) {
        boolean canHandle = true;
        if (updateWrapper.isCheckPts()) {
            canHandle = this.checkPts(updateWrapper);
        }
        if (canHandle) {
            TLObject update = updateWrapper.getUpdate();
            if (update instanceof TLUpdateShortMessage) {
                this.onTLUpdateShortMessage((TLUpdateShortMessage)update);
            } else if (update instanceof TLUpdateShortChatMessage) {
                this.onTLUpdateShortChatMessage((TLUpdateShortChatMessage)update);
            } else if (update instanceof TLUpdateShortSentMessage) {
                this.onTLUpdateShortSentMessage((TLUpdateShortSentMessage)update);
            } else if (update instanceof TLUpdateNewMessage) {
                this.onTLUpdateNewMessage((TLUpdateNewMessage)update);
            } else if (update instanceof TLUpdateChatParticipants) {
                this.onTLUpdateChatParticipants((TLUpdateChatParticipants)update);
            } else if (update instanceof TLUpdateChannelNewMessage) {
                this.onTLUpdateChannelNewMessage((TLUpdateChannelNewMessage)update);
            } else if (update instanceof TLUpdateChannel) {
                this.onTLUpdateChannel((TLUpdateChannel)update);
            } else if (update instanceof TLUpdateBotInlineQuery) {
                this.onTLUpdateBotInlineQuery((TLUpdateBotInlineQuery)update);
            } else if (update instanceof TLUpdateBotInlineSend) {
                this.onTLUpdateBotInlineSend((TLUpdateBotInlineSend)update);
            } else if (update instanceof TLUpdateChannelGroup) {
                this.onTLUpdateChannelGroup((TLUpdateChannelGroup)update);
            } else if (update instanceof TLUpdateChannelMessageViews) {
                this.onTLUpdateChannelMessageViews((TLUpdateChannelMessageViews)update);
            } else if (update instanceof TLUpdateChannelPinnedMessage) {
                this.onTLUpdateChannelPinnedMessage((TLUpdateChannelPinnedMessage)update);
            } else if (update instanceof TLUpdateChannelTooLong) {
                this.onTLUpdateChannelTooLong((TLUpdateChannelTooLong)update);
            } else if (update instanceof TLUpdateChatAdmin) {
                this.onTLUpdateChatAdmin((TLUpdateChatAdmin)update);
            } else if (update instanceof TLUpdateChatParticipantAdd) {
                this.onTLUpdateChatParticipantAdd((TLUpdateChatParticipantAdd)update);
            } else if (update instanceof TLUpdateChatParticipantAdmin) {
                this.onTLUpdateChatParticipantAdmin((TLUpdateChatParticipantAdmin)update);
            } else if (update instanceof TLUpdateChatParticipantDelete) {
                this.onTLUpdateChatParticipantDelete((TLUpdateChatParticipantDelete)update);
            } else if (update instanceof TLUpdateChatUserTyping) {
                this.onTLUpdateChatUserTyping((TLUpdateChatUserTyping)update);
            } else if (update instanceof TLUpdateContactLink) {
                this.onTLUpdateContactLink((TLUpdateContactLink)update);
            } else if (update instanceof TLUpdateContactRegistered) {
                this.onTLUpdateContactRegistered((TLUpdateContactRegistered)update);
            } else if (update instanceof TLUpdateDcOptions) {
                this.onTLUpdateDcOptions((TLUpdateDcOptions)update);
            } else if (update instanceof TLUpdateDeleteChannelMessages) {
                this.onTLUpdateDeleteChannelMessages((TLUpdateDeleteChannelMessages)update);
            } else if (update instanceof TLUpdateDeleteMessages) {
                this.onTLUpdateDeleteMessages((TLUpdateDeleteMessages)update);
            } else if (update instanceof TLUpdateEditChannelMessage) {
                this.onTLUpdateEditChannelMessage((TLUpdateEditChannelMessage)update);
            } else if (update instanceof TLUpdateMessageId) {
                this.onTLUpdateMessageId((TLUpdateMessageId)update);
            } else if (update instanceof TLUpdateNewAuthorization) {
                this.onTLUpdateNewAuthorization((TLUpdateNewAuthorization)update);
            } else if (update instanceof TLUpdateNewStickerSet) {
                this.onTLUpdateNewStickerSet((TLUpdateNewStickerSet)update);
            } else if (update instanceof TLUpdateNotifySettings) {
                this.onTLUpdateNotifySettings((TLUpdateNotifySettings)update);
            } else if (update instanceof TLUpdatePrivacy) {
                this.onTLUpdatePrivacy((TLUpdatePrivacy)update);
            } else if (update instanceof TLUpdateReadChannelInbox) {
                this.onTLUpdateReadChannelInbox((TLUpdateReadChannelInbox)update);
            } else if (update instanceof TLUpdateReadMessagesContents) {
                this.onTLUpdateReadMessagesContents((TLUpdateReadMessagesContents)update);
            } else if (update instanceof TLUpdateReadMessagesInbox) {
                this.onTLUpdateReadMessagesInbox((TLUpdateReadMessagesInbox)update);
            } else if (update instanceof TLUpdateReadMessagesOutbox) {
                this.onTLUpdateReadMessagesOutbox((TLUpdateReadMessagesOutbox)update);
            } else if (update instanceof TLUpdateSavedGifs) {
                this.onTLUpdateSavedGifs((TLUpdateSavedGifs)update);
            } else if (update instanceof TLUpdateServiceNotification) {
                this.onTLUpdateServiceNotification((TLUpdateServiceNotification)update);
            } else if (update instanceof TLUpdateStickerSets) {
                this.onTLUpdateStickerSets((TLUpdateStickerSets)update);
            } else if (update instanceof TLUpdateStickerSetsOrder) {
                this.onTLUpdateStickerSetsOrder((TLUpdateStickerSetsOrder)update);
            } else if (update instanceof TLUpdateUserBlocked) {
                this.onTLUpdateUserBlocked((TLUpdateUserBlocked)update);
            } else if (update instanceof TLUpdateUserName) {
                this.onTLUpdateUserName((TLUpdateUserName)update);
            } else if (update instanceof TLUpdateUserPhone) {
                this.onTLUpdateUserPhone((TLUpdateUserPhone)update);
            } else if (update instanceof TLUpdateUserPhoto) {
                this.onTLUpdateUserPhoto((TLUpdateUserPhoto)update);
            } else if (update instanceof TLUpdateUserStatus) {
                this.onTLUpdateUserStatus((TLUpdateUserStatus)update);
            } else if (update instanceof TLUpdateUserTyping) {
                this.onTLUpdateUserTyping((TLUpdateUserTyping)update);
            } else if (update instanceof TLUpdateWebPage) {
                this.onTLUpdateWebPage((TLUpdateWebPage)update);
            } else if (update instanceof TLFakeUpdate) {
                this.onTLFakeUpdate((TLFakeUpdate)update);
            } else if (update instanceof TLUpdateBotCallbackQuery) {
                this.onTLUpdateBotCallbackQuery((TLUpdateBotCallbackQuery)update);
            } else if (update instanceof TLUpdateEditMessage) {
                this.onTLUpdateEditMessage((TLUpdateEditMessage)update);
            } else if (update instanceof TLUpdateInlineBotCallbackQuery) {
                this.onTLUpdateInlineBotCallbackQuery((TLUpdateInlineBotCallbackQuery)update);
            }
            if (updateWrapper.isUpdatePts()) {
                this.updatePts(updateWrapper);
            }
        }
    }

    private boolean checkPts(UpdateWrapper updateWrapper) {
        return false;
    }

    @Override
    public final boolean checkSeq(int seq, int seqStart, int date) {
        return true;
    }

    @Override
    public final void getDifferences() {
    }

    private void updatePts(UpdateWrapper updateWrapper) {
    }

    private void onTLUpdateShortMessage(TLUpdateShortMessage update) {
        this.onTLUpdateShortMessageCustom(update);
    }

    private void onTLUpdateShortChatMessage(TLUpdateShortChatMessage update) {
        this.onTLUpdateShortChatMessageCustom(update);
    }

    private void onTLUpdateShortSentMessage(TLUpdateShortSentMessage update) {
        this.onTLUpdateShortSentMessageCustom(update);
    }

    private void onTLUpdateChatParticipants(TLUpdateChatParticipants update) {
        this.onTLUpdateChatParticipantsCustom(update);
    }

    private void onTLUpdateNewMessage(TLUpdateNewMessage update) {
        this.onTLUpdateNewMessageCustom(update);
    }

    private void onTLUpdateChannelNewMessage(TLUpdateChannelNewMessage update) {
        this.onTLUpdateChannelNewMessageCustom(update);
    }

    private void onTLUpdateChannel(TLUpdateChannel update) {
        this.onTLUpdateChannelCustom(update);
    }

    private void onTLUpdateBotInlineQuery(TLUpdateBotInlineQuery update) {
        this.onTLUpdateBotInlineQueryCustom(update);
    }

    private void onTLUpdateBotInlineSend(TLUpdateBotInlineSend update) {
        this.onTLUpdateBotInlineSendCustom(update);
    }

    private void onTLUpdateChannelGroup(TLUpdateChannelGroup update) {
        this.onTLUpdateChannelGroupCustom(update);
    }

    private void onTLUpdateChannelMessageViews(TLUpdateChannelMessageViews update) {
        this.onTLUpdateChannelMessageViewsCustom(update);
    }

    private void onTLUpdateChannelPinnedMessage(TLUpdateChannelPinnedMessage update) {
        this.onTLUpdateChannelPinnedMessageCustom(update);
    }

    private void onTLUpdateChannelTooLong(TLUpdateChannelTooLong update) {
    }

    private void onTLUpdateChatAdmin(TLUpdateChatAdmin update) {
        this.onTLUpdateChatAdminCustom(update);
    }

    private void onTLUpdateChatParticipantAdd(TLUpdateChatParticipantAdd update) {
        this.onTLUpdateChatParticipantAddCustom(update);
    }

    private void onTLUpdateChatParticipantAdmin(TLUpdateChatParticipantAdmin update) {
        this.onTLUpdateChatParticipantAdminCustom(update);
    }

    private void onTLUpdateChatParticipantDelete(TLUpdateChatParticipantDelete update) {
        this.onTLUpdateChatParticipantDeleteCustom(update);
    }

    private void onTLUpdateChatUserTyping(TLUpdateChatUserTyping update) {
        this.onTLUpdateChatUserTypingCustom(update);
    }

    private void onTLUpdateContactLink(TLUpdateContactLink update) {
        this.onTLUpdateContactLinkCustom(update);
    }

    private void onTLUpdateContactRegistered(TLUpdateContactRegistered update) {
        this.onTLUpdateContactRegisteredCustom(update);
    }

    private void onTLUpdateDcOptions(TLUpdateDcOptions update) {
        this.onTLUpdateDcOptionsCustom(update);
    }

    private void onTLUpdateDeleteChannelMessages(TLUpdateDeleteChannelMessages update) {
    }

    private void onTLUpdateDeleteMessages(TLUpdateDeleteMessages update) {
        this.onTLUpdateDeleteMessagesCustom(update);
    }

    private void onTLUpdateEditChannelMessage(TLUpdateEditChannelMessage update) {
    }

    private void onTLUpdateMessageId(TLUpdateMessageId update) {
        this.onTLUpdateMessageIdCustom(update);
    }

    private void onTLUpdateNewAuthorization(TLUpdateNewAuthorization update) {
        this.onTLUpdateNewAuthorizationCustom(update);
    }

    private void onTLUpdateNewStickerSet(TLUpdateNewStickerSet update) {
        this.onTLUpdateNewStickerSetCustom(update);
    }

    private void onTLUpdateNotifySettings(TLUpdateNotifySettings update) {
    }

    private void onTLUpdatePrivacy(TLUpdatePrivacy update) {
        this.onTLUpdatePrivacyCustom(update);
    }

    private void onTLUpdateReadChannelInbox(TLUpdateReadChannelInbox update) {
    }

    private void onTLUpdateReadMessagesContents(TLUpdateReadMessagesContents update) {
        this.onTLUpdateReadMessagesContentsCustom(update);
    }

    private void onTLUpdateReadMessagesInbox(TLUpdateReadMessagesInbox update) {
    }

    private void onTLUpdateReadMessagesOutbox(TLUpdateReadMessagesOutbox update) {
    }

    private void onTLUpdateSavedGifs(TLUpdateSavedGifs update) {
        this.onTLUpdateSavedGifsCustom(update);
    }

    private void onTLUpdateServiceNotification(TLUpdateServiceNotification update) {
        this.onTLUpdateServiceNotificationCustom(update);
    }

    private void onTLUpdateStickerSets(TLUpdateStickerSets update) {
        this.onTLUpdateStickerSetsCustom(update);
    }

    private void onTLUpdateStickerSetsOrder(TLUpdateStickerSetsOrder update) {
        this.onTLUpdateStickerSetsOrderCustom(update);
    }

    private void onTLUpdateUserBlocked(TLUpdateUserBlocked update) {
    }

    private void onTLUpdateUserName(TLUpdateUserName update) {
    }

    private void onTLUpdateUserPhone(TLUpdateUserPhone update) {
    }

    private void onTLUpdateUserPhoto(TLUpdateUserPhoto update) {
    }

    private void onTLUpdateUserStatus(TLUpdateUserStatus update) {
    }

    private void onTLUpdateUserTyping(TLUpdateUserTyping update) {
    }

    private void onTLUpdateWebPage(TLUpdateWebPage update) {
        this.onTLUpdateWebPageCustom(update);
    }

    private void onTLUpdateBotCallbackQuery(TLUpdateBotCallbackQuery update) {
    }

    private void onTLUpdateEditMessage(TLUpdateEditMessage update) {
    }

    private void onTLUpdateInlineBotCallbackQuery(TLUpdateInlineBotCallbackQuery update) {
    }

    @Override
    public final void updateStateModification(TLUpdatesState state) {
    }

    @Override
    public final void onTLUpdatesTooLong() {
    }

    private void onTLFakeUpdate(TLFakeUpdate update) {
        this.onTLFakeUpdateCustom(update);
    }

    @Override
    public final void onTLAbsDifference(TLAbsDifference absDifference) {
        this.onUsers(absDifference.getUsers());
        this.onChats(absDifference.getChats());
        absDifference.getNewMessages().stream().forEach(this::onTLAbsMessageCustom);
        absDifference.getOtherUpdates().stream().map(x -> {
            UpdateWrapper updateWrapper = new UpdateWrapper(x);
            updateWrapper.disablePtsCheck();
            updateWrapper.disableUpdatePts();
            return updateWrapper;
        }).forEach(this::processUpdate);
    }

    @Override
    public final void onTLChannelDifferences(List<TLAbsUser> users, List<TLAbsMessage> messages, List<TLAbsUpdate> newUpdates, List<TLAbsChat> chats) {
        this.onUsers(users);
        this.onChats(chats);
        messages.stream().forEach(this::onTLAbsMessageCustom);
        newUpdates.stream().map(x -> {
            UpdateWrapper updateWrapper = new UpdateWrapper(x);
            updateWrapper.disablePtsCheck();
            updateWrapper.disableUpdatePts();
            return updateWrapper;
        }).forEach(this::processUpdate);
    }

    @Override
    public final void onUsers(List<TLAbsUser> users) {
        this.onUsersCustom(users);
    }

    @Override
    public final void onChats(List<TLAbsChat> chats) {
        this.onChatsCustom(chats);
    }

    private boolean isUserFromMessageMissing(TLAbsMessage message, boolean checkChatId) {
        boolean isMissing = true;
        if (message instanceof TLMessage) {
            TLMessage tlMessage = (TLMessage)message;
            boolean isFromMissing = true;
            if (tlMessage.hasFromId()) {
                isFromMissing = this.isUserMissing(tlMessage.getFromId());
            }
            boolean isToMissing = true;
            if (tlMessage.getToId() instanceof TLPeerUser) {
                isToMissing = this.isUserMissing(tlMessage.getToId().getId());
            } else if (checkChatId) {
                isToMissing = this.isChatMissing(tlMessage.getChatId());
            }
            boolean isForwardedMissing = true;
            if (tlMessage.isForwarded()) {
                isForwardedMissing = this.isUserMissing(tlMessage.getFwdFrom().getFromId());
            }
            isMissing = isFromMissing && isToMissing && isForwardedMissing;
        } else if (message instanceof TLMessageService) {
            TLMessageService tlMessageService = (TLMessageService)message;
            boolean isFromMissing = true;
            if (tlMessageService.hasFromId()) {
                isFromMissing = this.isUserMissing(tlMessageService.getFromId());
            }
            boolean isToMissing = true;
            if (tlMessageService.getToId() instanceof TLPeerUser) {
                isToMissing = this.isUserMissing(tlMessageService.getToId().getId());
            } else if (checkChatId) {
                isToMissing = this.isChatMissing(tlMessageService.getChatId());
            }
            isMissing = isFromMissing && isToMissing;
        }
        return isMissing;
    }

    private boolean isUserFromMessageMissing(TLAbsMessage message) {
        return this.isUserFromMessageMissing(message, false);
    }

    private boolean isChatMissing(int chatId) {
        return true;
    }

    private boolean isUserMissing(int userId) {
        return true;
    }

    private boolean isPeerMissing(TLAbsPeer peer) {
        boolean isMissing = true;
        return true;
    }

    private boolean isNotifyPeerMissing(TLAbsNotifyPeer notifyPeer) {
        boolean isMissing = false;
        if (notifyPeer instanceof TLNotifyPeer) {
            isMissing = this.isPeerMissing(((TLNotifyPeer)notifyPeer).getPeer());
        }
        return isMissing;
    }

    private boolean isUserFromShortMessageMissing(TLUpdateShortMessage updateShortMessage) {
        return true;
    }

    protected abstract void onTLUpdateChatParticipantsCustom(TLUpdateChatParticipants var1);

    protected abstract void onTLUpdateNewMessageCustom(TLUpdateNewMessage var1);

    protected abstract void onTLUpdateChannelNewMessageCustom(TLUpdateChannelNewMessage var1);

    protected abstract void onTLUpdateChannelCustom(TLUpdateChannel var1);

    protected abstract void onTLUpdateBotInlineQueryCustom(TLUpdateBotInlineQuery var1);

    protected abstract void onTLUpdateBotInlineSendCustom(TLUpdateBotInlineSend var1);

    protected abstract void onTLUpdateChannelGroupCustom(TLUpdateChannelGroup var1);

    protected abstract void onTLUpdateChannelMessageViewsCustom(TLUpdateChannelMessageViews var1);

    protected abstract void onTLUpdateChannelPinnedMessageCustom(TLUpdateChannelPinnedMessage var1);

    protected abstract void onTLUpdateChatAdminCustom(TLUpdateChatAdmin var1);

    protected abstract void onTLUpdateChatParticipantAddCustom(TLUpdateChatParticipantAdd var1);

    protected abstract void onTLUpdateChatParticipantAdminCustom(TLUpdateChatParticipantAdmin var1);

    protected abstract void onTLUpdateChatParticipantDeleteCustom(TLUpdateChatParticipantDelete var1);

    protected abstract void onTLUpdateChatUserTypingCustom(TLUpdateChatUserTyping var1);

    protected abstract void onTLUpdateContactLinkCustom(TLUpdateContactLink var1);

    protected abstract void onTLUpdateContactRegisteredCustom(TLUpdateContactRegistered var1);

    protected abstract void onTLUpdateDcOptionsCustom(TLUpdateDcOptions var1);

    protected abstract void onTLUpdateDeleteChannelMessagesCustom(TLUpdateDeleteChannelMessages var1);

    protected abstract void onTLUpdateDeleteMessagesCustom(TLUpdateDeleteMessages var1);

    protected abstract void onTLUpdateEditChannelMessageCustom(TLUpdateEditChannelMessage var1);

    protected abstract void onTLUpdateMessageIdCustom(TLUpdateMessageId var1);

    protected abstract void onTLUpdateNewAuthorizationCustom(TLUpdateNewAuthorization var1);

    protected abstract void onTLUpdateNewStickerSetCustom(TLUpdateNewStickerSet var1);

    protected abstract void onTLUpdateNotifySettingsCustom(TLUpdateNotifySettings var1);

    protected abstract void onTLUpdatePrivacyCustom(TLUpdatePrivacy var1);

    protected abstract void onTLUpdateReadChannelInboxCustom(TLUpdateReadChannelInbox var1);

    protected abstract void onTLUpdateReadMessagesContentsCustom(TLUpdateReadMessagesContents var1);

    protected abstract void onTLUpdateReadMessagesInboxCustom(TLUpdateReadMessagesInbox var1);

    protected abstract void onTLUpdateReadMessagesOutboxCustom(TLUpdateReadMessagesOutbox var1);

    protected abstract void onTLUpdateSavedGifsCustom(TLUpdateSavedGifs var1);

    protected abstract void onTLUpdateServiceNotificationCustom(TLUpdateServiceNotification var1);

    protected abstract void onTLUpdateStickerSetsCustom(TLUpdateStickerSets var1);

    protected abstract void onTLUpdateStickerSetsOrderCustom(TLUpdateStickerSetsOrder var1);

    protected abstract void onTLUpdateUserBlockedCustom(TLUpdateUserBlocked var1);

    protected abstract void onTLUpdateUserNameCustom(TLUpdateUserName var1);

    protected abstract void onTLUpdateUserPhoneCustom(TLUpdateUserPhone var1);

    protected abstract void onTLUpdateUserPhotoCustom(TLUpdateUserPhoto var1);

    protected abstract void onTLUpdateUserStatusCustom(TLUpdateUserStatus var1);

    protected abstract void onTLUpdateUserTypingCustom(TLUpdateUserTyping var1);

    protected abstract void onTLUpdateWebPageCustom(TLUpdateWebPage var1);

    protected abstract void onTLFakeUpdateCustom(TLFakeUpdate var1);

    protected abstract void onTLUpdateShortMessageCustom(TLUpdateShortMessage var1);

    protected abstract void onTLUpdateShortChatMessageCustom(TLUpdateShortChatMessage var1);

    protected abstract void onTLUpdateShortSentMessageCustom(TLUpdateShortSentMessage var1);

    protected abstract void onTLUpdateBotCallbackQueryCustom(TLUpdateBotCallbackQuery var1);

    protected abstract void onTLUpdateEditMessageCustom(TLUpdateEditMessage var1);

    protected abstract void onTLUpdateInlineBotCallbackQueryCustom(TLUpdateInlineBotCallbackQuery var1);

    protected abstract void onTLAbsMessageCustom(TLAbsMessage var1);

    protected abstract void onUsersCustom(List<TLAbsUser> var1);

    protected abstract void onChatsCustom(List<TLAbsChat> var1);
}

