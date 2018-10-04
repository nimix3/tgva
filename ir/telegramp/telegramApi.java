
package ir.telegramp;

import engine.MemoryApiState;
import ir.telegramp.IUpdatesHandler;
import ir.telegramp.JarPath;
import ir.telegramp.MimeTypeMap;
import ir.telegramp.MyLogger;
import ir.telegramp.UpdateWrapper;
import ir.telegramp.UpdatesHandlerBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Stream;
import org.telegram.api.TLConfig;
import org.telegram.api.TLDcOption;
import org.telegram.api.account.TLAbsAccountPassword;
import org.telegram.api.account.TLAccountNoPassword;
import org.telegram.api.account.TLAccountPassword;
import org.telegram.api.account.TLAccountPasswordInputSettings;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.api.auth.TLCheckedPhone;
import org.telegram.api.auth.TLExportedAuthorization;
import org.telegram.api.auth.TLSentCode;
import org.telegram.api.channel.TLChannelParticipants;
import org.telegram.api.channel.filters.TLAbsChannelMessagesFilter;
import org.telegram.api.channel.filters.TLChannelMessagesFilterEmpty;
import org.telegram.api.channel.participants.TLAbsChannelParticipant;
import org.telegram.api.channel.participants.filters.TLAbsChannelParticipantsFilter;
import org.telegram.api.channel.participants.filters.TLChannelParticipantsFilterRecent;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.channel.TLChannel;
import org.telegram.api.chat.invite.TLChatInviteAlready;
import org.telegram.api.chat.invite.TLChatInviteExported;
import org.telegram.api.chat.participant.chatparticipants.TLAbsChatParticipants;
import org.telegram.api.contacts.TLAbsContacts;
import org.telegram.api.contacts.TLContacts;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.contacts.TLResolvedPeer;
import org.telegram.api.dialog.TLAbsDialog;
import org.telegram.api.document.attribute.TLAbsDocumentAttribute;
import org.telegram.api.document.attribute.TLDocumentAttributeFilename;
import org.telegram.api.engine.ApiCallback;
import org.telegram.api.engine.AppInfo;
import org.telegram.api.engine.Logger;
import org.telegram.api.engine.LoggerInterface;
import org.telegram.api.engine.RpcCallback;
import org.telegram.api.engine.RpcException;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.engine.file.DownloadListener;
import org.telegram.api.engine.file.Downloader;
import org.telegram.api.engine.file.UploadListener;
import org.telegram.api.engine.file.Uploader;
import org.telegram.api.engine.storage.AbsApiState;
import org.telegram.api.file.location.TLAbsFileLocation;
import org.telegram.api.file.location.TLFileLocation;
import org.telegram.api.functions.account.TLRequestAccountChangePhone;
import org.telegram.api.functions.account.TLRequestAccountCheckUsername;
import org.telegram.api.functions.account.TLRequestAccountDeleteAccount;
import org.telegram.api.functions.account.TLRequestAccountGetPassword;
import org.telegram.api.functions.account.TLRequestAccountResetNotifySettings;
import org.telegram.api.functions.account.TLRequestAccountSendChangePhoneCode;
import org.telegram.api.functions.account.TLRequestAccountUpdatePasswordSettings;
import org.telegram.api.functions.account.TLRequestAccountUpdateProfile;
import org.telegram.api.functions.account.TLRequestAccountUpdateStatus;
import org.telegram.api.functions.account.TLRequestAccountUpdateUsername;
import org.telegram.api.functions.auth.TLRequestAuthCheckPassword;
import org.telegram.api.functions.auth.TLRequestAuthCheckPhone;
import org.telegram.api.functions.auth.TLRequestAuthExportAuthorization;
import org.telegram.api.functions.auth.TLRequestAuthImportAuthorization;
import org.telegram.api.functions.auth.TLRequestAuthImportBotAuthorization;
import org.telegram.api.functions.auth.TLRequestAuthResetAuthorizations;
import org.telegram.api.functions.auth.TLRequestAuthSendCode;
import org.telegram.api.functions.auth.TLRequestAuthSignIn;
import org.telegram.api.functions.auth.TLRequestAuthSignUp;
import org.telegram.api.functions.channels.TLRequestChannelsCreateChannel;
import org.telegram.api.functions.channels.TLRequestChannelsDeleteChannel;
import org.telegram.api.functions.channels.TLRequestChannelsEditPhoto;
import org.telegram.api.functions.channels.TLRequestChannelsExportInvite;
import org.telegram.api.functions.channels.TLRequestChannelsGetDialogs;
import org.telegram.api.functions.channels.TLRequestChannelsGetImportantHistory;
import org.telegram.api.functions.channels.TLRequestChannelsGetParticipants;
import org.telegram.api.functions.channels.TLRequestChannelsInviteToChannel;
import org.telegram.api.functions.channels.TLRequestChannelsJoinChannel;
import org.telegram.api.functions.channels.TLRequestChannelsKickFromChannel;
import org.telegram.api.functions.channels.TLRequestChannelsLeaveChannel;
import org.telegram.api.functions.channels.TLRequestChannelsReadHistory;
import org.telegram.api.functions.channels.TLRequestChannelsToggleComments;
import org.telegram.api.functions.channels.TLRequestChannelsUpdateUsername;
import org.telegram.api.functions.contacts.TLRequestContactsDeleteContact;
import org.telegram.api.functions.contacts.TLRequestContactsDeleteContacts;
import org.telegram.api.functions.contacts.TLRequestContactsGetContacts;
import org.telegram.api.functions.contacts.TLRequestContactsImportContacts;
import org.telegram.api.functions.contacts.TLRequestContactsResolveUsername;
import org.telegram.api.functions.help.TLRequestHelpGetConfig;
import org.telegram.api.functions.messages.TLRequestMessagesAddChatUser;
import org.telegram.api.functions.messages.TLRequestMessagesCheckChatInvite;
import org.telegram.api.functions.messages.TLRequestMessagesCreateChat;
import org.telegram.api.functions.messages.TLRequestMessagesDeleteChatUser;
import org.telegram.api.functions.messages.TLRequestMessagesDeleteHistory;
import org.telegram.api.functions.messages.TLRequestMessagesEditChatAdmin;
import org.telegram.api.functions.messages.TLRequestMessagesEditChatPhoto;
import org.telegram.api.functions.messages.TLRequestMessagesForwardMessage;
import org.telegram.api.functions.messages.TLRequestMessagesGetDialogs;
import org.telegram.api.functions.messages.TLRequestMessagesGetFullChat;
import org.telegram.api.functions.messages.TLRequestMessagesGetHistory;
import org.telegram.api.functions.messages.TLRequestMessagesGetMessagesViews;
import org.telegram.api.functions.messages.TLRequestMessagesHideReportSpam;
import org.telegram.api.functions.messages.TLRequestMessagesImportChatInvite;
import org.telegram.api.functions.messages.TLRequestMessagesMigrateChat;
import org.telegram.api.functions.messages.TLRequestMessagesSendMedia;
import org.telegram.api.functions.messages.TLRequestMessagesSendMessage;
import org.telegram.api.functions.messages.TLRequestMessagesToggleChatAdmins;
import org.telegram.api.functions.photos.TLRequestPhotosGetUserPhotos;
import org.telegram.api.functions.photos.TLRequestPhotosUpdateProfilePhoto;
import org.telegram.api.functions.photos.TLRequestPhotosUploadProfilePhoto;
import org.telegram.api.functions.updates.TLRequestUpdatesGetChannelDifference;
import org.telegram.api.functions.updates.TLRequestUpdatesGetDifference;
import org.telegram.api.functions.updates.TLRequestUpdatesGetState;
import org.telegram.api.functions.users.TLRequestUsersGetFullUser;
import org.telegram.api.input.TLInputPhoneContact;
import org.telegram.api.input.chat.TLAbsInputChannel;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.api.input.chat.photo.TLAbsInputChatPhoto;
import org.telegram.api.input.chat.photo.TLInputChatUploadedPhoto;
import org.telegram.api.input.file.TLAbsInputFile;
import org.telegram.api.input.file.TLInputFile;
import org.telegram.api.input.file.TLInputFileBig;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.api.input.filelocation.TLInputFileLocation;
import org.telegram.api.input.geopoint.TLAbsInputGeoPoint;
import org.telegram.api.input.geopoint.TLInputGeoPointEmpty;
import org.telegram.api.input.media.TLAbsInputMedia;
import org.telegram.api.input.media.TLInputMediaUploadedDocument;
import org.telegram.api.input.media.TLInputMediaUploadedPhoto;
import org.telegram.api.input.peer.TLAbsInputPeer;
import org.telegram.api.input.peer.TLInputPeerChannel;
import org.telegram.api.input.peer.TLInputPeerChat;
import org.telegram.api.input.peer.TLInputPeerEmpty;
import org.telegram.api.input.peer.TLInputPeerUser;
import org.telegram.api.input.photo.TLAbsInputPhoto;
import org.telegram.api.input.photo.TLInputPhotoEmpty;
import org.telegram.api.input.photo.crop.TLAbsInputPhotoCrop;
import org.telegram.api.input.photo.crop.TLInputPhotoCropAuto;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.input.user.TLInputUser;
import org.telegram.api.input.user.TLInputUserSelf;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.TLMessage;
import org.telegram.api.message.entity.TLAbsMessageEntity;
import org.telegram.api.message.entity.TLMessageEntityBold;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.messages.TLMessagesChatFull;
import org.telegram.api.messages.TLMessagesSlice;
import org.telegram.api.messages.dialogs.TLAbsDialogs;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.photo.TLPhoto;
import org.telegram.api.photo.size.TLAbsPhotoSize;
import org.telegram.api.photo.size.TLPhotoSize;
import org.telegram.api.photos.TLPhotos;
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
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.api.updates.TLUpdateShort;
import org.telegram.api.updates.TLUpdateShortChatMessage;
import org.telegram.api.updates.TLUpdateShortMessage;
import org.telegram.api.updates.TLUpdateShortSentMessage;
import org.telegram.api.updates.TLUpdates;
import org.telegram.api.updates.TLUpdatesCombined;
import org.telegram.api.updates.TLUpdatesTooLong;
import org.telegram.api.updates.channel.differences.TLAbsUpdatesChannelDifferences;
import org.telegram.api.updates.channel.differences.TLUpdatesChannelDifferencesEmpty;
import org.telegram.api.updates.channel.differences.TLUpdatesChannelDifferencesTooLong;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.api.updates.difference.TLDifferenceSlice;
import org.telegram.api.user.TLAbsUser;
import org.telegram.api.user.TLUser;
import org.telegram.api.user.TLUserFull;
import org.telegram.api.user.profile.photo.TLAbsUserProfilePhoto;
import org.telegram.api.user.profile.photo.TLUserProfilePhoto;
import org.telegram.mtproto.log.LogInterface;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLBoolTrue;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLStringVector;
import org.telegram.tl.TLVector;
import org.telegram.tl.util.ArrayUtils;

public class telegramApi {
    public TLUser bot = null;
    private MemoryApiState apiState;
    public TelegramApi api;
    private Random rnd = new Random();
    public String SenderPhoneNo = "";
    public String tzid = "";
    public static String token = "";
    public static String Iname = "";
    public static String Ifamily = "";
    public TLAbsContacts AccountContacts = null;
    public int index;
    public TLChannel channel;
    public TLStringVector extractedNumbers = new TLStringVector();
    public TLIntVector extractedIds = new TLIntVector();
    public TLSentCode sentCode = null;
    public int chatId = 0;
    public boolean flood_wait = false;
    public int flood_time = 0;
    public boolean peer_flood = false;
    public int sended_files = 0;
    public String sent_result = "";
    public int sended_messages = 0;
    public String last_message = "";
    public int dcId = 2;
    public String DeviceModel = "Samsung Galaxy Core II G355";
    public String systemVersion = "SDK 19";
    public String appVersion = "3.7.1";
    public String langCode = "en";
    public String Senders_path = "";
    public boolean AuthInvalidated = false;
    public boolean Destroyed = false;
    public String[] files = new String[]{"", "", "", "", "", "", ""};
    private UpdateHandlerThread updateHandlerThread;
    private IUpdatesHandler updatesHandler;
    TLInputMediaUploadedPhoto doc = null;
    TLInputMediaUploadedDocument vdoc = null;
    String Message = null;
    static int apiId = 6;
    static String apiHash = "eb06d4abfb49dc3eeb1aeb98ae0f581e";
    public String date;
    public int Dcounter = 1;
    public TLSentCode sentChangePhoneCode = null;
    int fromid = 0;
    public static SecureRandom random = new SecureRandom();
    int r = 0;
    private Thread[] mythread = null;
    TLVector us = new TLVector();
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    static int i = 0;
    static int cvid = 0;
    static long cvhash = 0L;
    public long chathash = 0L;
    public static TLConfig serverConfiguration = null;

    public void initial() {
        this.disableLogging();
        this.api = new TelegramApi(new MemoryApiState(false), new AppInfo(apiId, this.DeviceModel, this.systemVersion, this.appVersion, this.langCode), new ApiCallback(){

            @Override
            public void onAuthCancelled(TelegramApi api) {
            }

            @Override
            public void onUpdatesInvalidated(TelegramApi api) {
            }

            @Override
            public void onUpdate(TLAbsUpdates updates) {
                if (updates instanceof TLUpdateShortMessage) {
                    telegramApi.this.onIncomingMessageUser(((TLUpdateShortMessage)updates).getUserId(), ((TLUpdateShortMessage)updates).getMessage());
                }
            }
        });
        this.logWithSenderNumber("initialized.");
    }

    public byte[] _Last(String in) {
        try {
            return in.getBytes("utf-8");
        }
        catch (Exception ex) {
            return null;
        }
    }

    public void joinbot(String token) throws IOException {
        this.logWithSenderNumber("login bot....");
        this.createApi(apiId, null, false);
        TLConfig config = telegramApi.loadServerConfigurations(this.api);
        this.apiState.updateSettings(config);
        int destDC = 0;
        TLRequestAuthImportBotAuthorization BotAuthorization = new TLRequestAuthImportBotAuthorization();
        try {
            BotAuthorization.setFlags(0);
            BotAuthorization.setApiHash(apiHash);
            BotAuthorization.setApiId(apiId);
            BotAuthorization.setBotAuthToken(token);
            this.api.doRpcCallNonAuth(BotAuthorization);
        }
        catch (RpcException e) {
            if (e.getErrorCode() == 303) {
                if (e.getErrorTag().startsWith("NETWORK_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("NETWORK_MIGRATE_".length()));
                } else if (e.getErrorTag().startsWith("PHONE_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("PHONE_MIGRATE_".length()));
                } else if (e.getErrorTag().startsWith("USER_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("USER_MIGRATE_".length()));
                }
                this.dcId = destDC;
                this.api.switchToDc(destDC);
                try {
                    this.api.doRpcCallNonAuth(BotAuthorization);
                }
                catch (RpcException ex) {
                    this.logWithSenderNumber("Err." + ex.getErrorTag());
                    java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (TimeoutException ex) {
                    java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (TimeoutException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.logWithSenderNumber("completed.");
        this.apiState.setAuthenticated(this.apiState.getPrimaryDc(), true);
    }

    public void createChat2(TLVector _users, String title, boolean addbot) {
        try {
            if (addbot) {
                TLInputUser p2 = new TLInputUser();
                p2.setUserId(this.bot.getId());
                p2.setAccessHash(this.bot.getAccessHash());
                _users.add(p2);
            }
            TLRequestMessagesCreateChat mcc = new TLRequestMessagesCreateChat();
            mcc.setTitle(title);
            mcc.setUsers(_users);
            this.chatId = ((TLUpdates)this.api.doRpcCall(mcc)).getChats().get(0).getId();
            this.logWithSenderNumber("Created.");
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return;
        }
    }

    public void createChat(int id, String title, boolean addbot) {
        TLVector<TLAbsInputUser> _users = new TLVector<TLAbsInputUser>();
        try {
            TLInputUser p = new TLInputUser();
            p.setUserId(id);
            _users.add(p);
            if (addbot) {
                TLInputUser p2 = new TLInputUser();
                p2.setUserId(this.bot.getId());
                p2.setAccessHash(this.bot.getAccessHash());
                _users.add(p2);
            }
            TLRequestMessagesCreateChat mcc = new TLRequestMessagesCreateChat();
            mcc.setTitle(title);
            mcc.setUsers(_users);
            this.chatId = ((TLUpdates)this.api.doRpcCall(mcc)).getChats().get(0).getId();
            this.logWithSenderNumber("Created.");
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return;
        }
    }

    public boolean changeAccountPassword(String old_password, String new_password) {
        try {
            TLAbsAccountPassword absAccountPassword = null;
            try {
                absAccountPassword = (TLAccountPassword)this.api.doRpcCallNonAuth(new TLRequestAccountGetPassword());
            }
            catch (Exception ex) {
                absAccountPassword = (TLAccountNoPassword)this.api.doRpcCallNonAuth(new TLRequestAccountGetPassword());
            }
            byte[] salt = new byte[absAccountPassword.getNewSalt().getLength() + 8];
            random.nextBytes(salt);
            System.arraycopy(absAccountPassword.getNewSalt().getData(), 0, salt, 0, absAccountPassword.getNewSalt().getLength());
            absAccountPassword.setNewSalt(new TLBytes(salt));
            TLRequestAccountUpdatePasswordSettings passwordSettings = new TLRequestAccountUpdatePasswordSettings();
            if (old_password.equalsIgnoreCase("")) {
                passwordSettings.setCurrentPasswordHash(new TLBytes(new byte[0]));
            } else {
                passwordSettings.setCurrentPasswordHash(new TLBytes(telegramApi.getPasswordHash(old_password, ((TLAccountPassword)absAccountPassword).getCurrentSalt())));
            }
            TLAccountPasswordInputSettings set = new TLAccountPasswordInputSettings();
            if (new_password.isEmpty()) {
                if (absAccountPassword instanceof TLAccountNoPassword) {
                    passwordSettings.setCurrentPasswordHash(new TLBytes(new byte[0]));
                    set.setFlags(0);
                    set.setEmail("");
                } else {
                    set.setFlags(1);
                    set.setNewPasswordHash(new TLBytes(new byte[0]));
                    set.setNewSalt(new TLBytes(new byte[0]));
                    set.setEmail("");
                    set.setHint("");
                }
            } else {
                byte[] newPasswordBytes = null;
                try {
                    newPasswordBytes = new_password.getBytes("UTF-8");
                }
                catch (Exception e) {
                    this.logWithSenderNumber(e.getMessage());
                }
                byte[] new_salt = absAccountPassword.getNewSalt().getData();
                byte[] hash = new byte[new_salt.length * 2 + newPasswordBytes.length];
                System.arraycopy(new_salt, 0, hash, 0, new_salt.length);
                System.arraycopy(newPasswordBytes, 0, hash, new_salt.length, newPasswordBytes.length);
                System.arraycopy(new_salt, 0, hash, hash.length - new_salt.length, new_salt.length);
                set.setFlags(1);
                set.setEmail("");
                set.setHint("telegramP.tips");
                set.setNewPasswordHash(new TLBytes(telegramApi.computeSHA256(hash, 0, hash.length)));
                set.setNewSalt(new TLBytes(new_salt));
            }
            passwordSettings.setNewSettings(set);
            if (this.api.doRpcCall(passwordSettings) instanceof TLBoolTrue) {
                return true;
            }
            return false;
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            System.out.print(ex.getErrorTag());
        }
        catch (IOException ex) {
            System.out.print(ex.toString());
        }
        catch (TimeoutException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    public int countchannel() {
        TLRequestChannelsGetDialogs gd = new TLRequestChannelsGetDialogs();
        gd.setLimit(100000);
        gd.setOffset(0);
        TLAbsDialogs dialogs = null;
        try {
            dialogs = (TLAbsDialogs)this.api.doRpcCall(gd);
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (TimeoutException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.r = 0;
        dialogs.getChats().stream().forEach(c -> {
            if (c instanceof TLChannel) {
                TLChannel cv = (TLChannel)c;
                ++this.r;
                System.out.println("Channel " + cv.getUsername() + "\r\n");
            }
        });
        return this.r;
    }

    public boolean checkNetwork(int TimeOut) {
        try {
            TLRequestAuthSignIn si = new TLRequestAuthSignIn();
            si.setPhoneCode("12345");
            si.setPhoneNumber("" + this.rnd.nextLong() + "");
            si.setPhoneCodeHash("" + this.rnd.nextLong() + "");
            this.api.doRpcCallNonAuth(si, TimeOut);
            return true;
        }
        catch (Exception ex) {
            try {
                return false;
            }
            catch (Exception ex2) {
                return false;
            }
        }
    }

    public String createChannel(String title, String about) {
        try {
            TLRequestChannelsCreateChannel chh = new TLRequestChannelsCreateChannel();
            chh.setAbout(about);
            chh.setTitle(title);
            chh.setcomputeFlags(true, false);
            this.channel = (TLChannel)((TLUpdates)this.api.doRpcCall(chh)).getChats().get(0);
            this.logWithSenderNumber("Channel '" + title + "' Created.");
            return "created";
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return "timeOut";
        }
    }

    public void destroy() {
        block9 : {
            try {
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
                this.Destroyed = true;
                this.updateHandlerThread.interrupt();
                try {
                    if (this.api != null) {
                        this.api.close();
                        this.api = null;
                        boolean cont = false;
                        for (Thread t : threadArray) {
                            try {
                                if (t.getName().contains("#")) {
                                    t.interrupt();
                                }
                                if (!t.getName().contains("Selector Thread")) continue;
                                t.interrupt();
                                t.stop();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        this.logWithSenderNumber("Api Destroyed.");
                        break block9;
                    }
                    System.gc();
                    System.runFinalization();
                }
                catch (Exception ex) {
                    System.gc();
                    System.runFinalization();
                }
            }
            catch (Exception ex) {
                System.gc();
                System.runFinalization();
            }
        }
    }

    public boolean checkAuth(int timeOut) {
        try {
            TLRequestAccountUpdateStatus tau = new TLRequestAccountUpdateStatus();
            tau.setOffline(false);
            TLBool tlboolean = (TLBool)this.api.doRpcCall(tau, timeOut);
            return true;
        }
        catch (Exception ex) {
            if (!this.AuthInvalidated && timeOut < 4500) {
                return this.checkAuth(timeOut + 1000);
            }
            return false;
        }
    }

    public telegramApi(String Phone) {
        try {
            this.SenderPhoneNo = Phone;
            MemoryApiState _loadState = null;
            this.disableLogging();
            _loadState = this.loadState(this.SenderPhoneNo);
            if (_loadState == null) {
                this.createApi(apiId, null, false);
                this.login(this.SenderPhoneNo, apiId, apiHash, false);
            } else {
                this.createApi(apiId, _loadState, false);
                this.login();
            }
        }
        catch (Exception ex) {
            System.out.println("Err:" + this.SenderPhoneNo);
            System.err.println(ex.getMessage());
            this.Mov_block(this.SenderPhoneNo);
        }
    }

    public telegramApi(int _index) {
        this.index = _index;
    }

    public telegramApi() {
        try {
            this.disableLogging();
            System.setProperty("file.encoding", "UTF-8");
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
            int apiId = 6;
            String apiHash = "eb06d4abfb49dc3eeb1aeb98ae0f581e";
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            Thread[] arrthread = threadSet.toArray(new Thread[threadSet.size()]);
        }
        catch (Exception ex) {
            this.logWithSenderNumber(ex.getMessage());
        }
    }

    private void onIncomingMessageUser(int uid, String message) {
        this.last_message = message;
        File file = new File(JarPath.GetPath() + "\\receivedMessages.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            Files.write(file.toPath(), ("\r\nMessage from member id:" + uid + " to:" + this.SenderPhoneNo + " => " + this.last_message + "\n").getBytes("utf-8"), StandardOpenOption.APPEND);
        }
        catch (IOException ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
        }
        System.out.println(message);
    }

    public String uploadFile(String FileAddress, int index, String caption) {
        if (!new File(FileAddress).exists()) {
            this.logWithSenderNumber("File not found. :|");
            return "File not found";
        }
        try {
            if (caption == null || caption == "null" || caption.isEmpty()) {
                caption = "";
            }
        }
        catch (Exception ex) {
            return "Err." + ex.getMessage();
        }
        String file_name = FileAddress.substring(FileAddress.lastIndexOf(92) + 1);
        this.logWithSenderNumber("Uploading " + file_name + "...");
        int task = this.api.getUploader().requestTask(FileAddress, null);
        this.api.getUploader().waitForTask(task);
        int resultState = this.api.getUploader().getTaskState(task);
        Uploader.UploadResult result = this.api.getUploader().getUploadResult(task);
        this.files[index] = "uploaded;" + file_name + ";" + (result.isUsedBigFile() ? "big" : "small") + ";" + result.getFileId() + ";" + result.getPartsCount() + ";" + result.getHash() + ";" + caption;
        this.logWithSenderNumber("Uploaded.");
        return this.files[index];
    }

    public void downloadProfilePic(int _userID) {
        try {
            System.out.println("Getting user full info...");
            TLRequestUsersGetFullUser getful = new TLRequestUsersGetFullUser();
            TLAbsInputUser peer = null;
            if (_userID != 0) {
                peer = new TLInputUser();
                peer.setUserId(_userID);
            } else {
                peer = new TLInputUserSelf();
            }
            getful.setId(peer);
            TLUserFull tlUserFull = (TLUserFull)this.api.doRpcCall(getful);
            if (tlUserFull.getProfilePhoto() == null) {
                System.out.println("User does not have picture.");
                return;
            }
            String str = JarPath.GetPath() + "\\Downloads\\" + _userID;
            new File(str).mkdirs();
            System.out.print("Downloading profile pictures.");
            TLRequestPhotosGetUserPhotos pu = new TLRequestPhotosGetUserPhotos();
            pu.setUserId(peer);
            pu.setLimit(0);
            pu.setMaxId(0);
            pu.setOffset(0);
            TLPhotos tlPhotos = (TLPhotos)this.api.doRpcCall(pu);
            for (int i = 0; i < tlPhotos.getPhotos().size(); ++i) {
                TLFileLocation tlFileLocation = (TLFileLocation)((TLPhotoSize)((TLPhoto)tlPhotos.getPhotos().get(i)).getSizes().get(2)).getLocation();
                TLInputFileLocation inputFileLocation = new TLInputFileLocation();
                inputFileLocation.setLocalId(tlFileLocation.getLocalId());
                inputFileLocation.setSecret(tlFileLocation.getSecret());
                inputFileLocation.setVolumeId(tlFileLocation.getVolumeId());
                this.api.getDownloader().waitForTask(this.api.getDownloader().requestTask(tlFileLocation.getDcId(), inputFileLocation, ((TLPhotoSize)((TLPhoto)tlPhotos.getPhotos().get(i)).getSizes().get(2)).getSize(), str + "\\" + (i + 1) + ".jpg", (DownloadListener)((Object)new telegramApi())));
            }
            System.out.println("\r\nDownload complated.");
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return;
        }
    }

    public String uploadFile(String FileAddress, int index) {
        return this.uploadFile(FileAddress, index, "");
    }

    public String sendFileToPhone(String PhoneNo, String file) {
        return this.sendFileToContact(PhoneNo, file, false, "private", false);
    }

    public String sendFileByUid(int Uid, String file) {
        return this.sendFileToContact("" + Uid + "", file, true, "private", false);
    }

    public String sendFileByUid(int Uid, String file, String chat_type, boolean silent) {
        return this.sendFileToContact("" + Uid + "", file, true, chat_type, silent);
    }

    public String getContactId(String PhoneNo) {
        TLInputPhoneContact tlInputContact = new TLInputPhoneContact();
        tlInputContact.setClientId(1L);
        tlInputContact.setFirstName(PhoneNo);
        tlInputContact.setLastName("c");
        tlInputContact.setPhone(PhoneNo);
        TLVector<TLInputPhoneContact> _contacts = new TLVector<TLInputPhoneContact>();
        _contacts.add(tlInputContact);
        try {
            int i = 0;
            TLRequestContactsImportContacts req = new TLRequestContactsImportContacts();
            req.setContacts(_contacts);
            req.setReplace(true);
            TLVector<TLAbsUser> users = ((TLImportedContacts)this.api.doRpcCall(req)).getUsers();
            if (users.size() > 0) {
                i = users.get(0).getId();
            }
            if (i > 0) {
                this.logWithSenderNumber(PhoneNo + " added successfully.");
                return "" + i + "";
            }
            this.logWithSenderNumber(" Error in add. Is a telegram member?");
            return "Error in add. Is a telegram member?";
        }
        catch (Exception ex) {
            return "Err: " + ex.getMessage();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String sendFileToContact(String obj0, String fileName, boolean obj2, String obj3, boolean obj4) {
        String[] strArray = null;
        Object _peer2 = null;
        try {
            strArray = fileName.split(";");
            if (!strArray[0].equalsIgnoreCase("uploaded")) {
                this.logWithSenderNumber("File not uploaded!. :|");
                return "File Not Uploaded";
            }
            if (obj3.contains("group")) {
                TLInputPeerChat ch = new TLInputPeerChat();
                ch.setChatId(this.chatId);
                TLInputPeerChat _peer2 = ch;
            } else if (obj3.contains("bot")) {
                if (!this.bot.isBot()) return null;
                TLInputPeerUser _peer2 = new TLInputPeerUser();
                _peer2.setUserId(this.bot.getId());
                _peer2.setAccessHash(this.bot.getAccessHash());
            } else if (obj3.contains("channel")) {
                this.logWithSenderNumber("channel file...");
                TLInputPeerChannel chh = new TLInputPeerChannel();
                chh.setAccessHash(this.channel.getAccessHash());
                chh.setChannelId(this.channel.getId());
                TLInputPeerChannel _peer2 = chh;
            } else {
                TLInputPeerUser _peer2 = new TLInputPeerUser();
                if (!obj2) {
                    try {
                        _peer2 = this.addContact(obj0);
                    }
                    catch (Exception chh) {}
                } else {
                    TLInputPeerUser us = new TLInputPeerUser();
                    us.setUserId(Integer.parseInt(obj0));
                    _peer2 = us;
                }
            }
        }
        catch (Exception us) {
            // empty catch block
        }
        try {
            TLAbsInputFile inputFile;
            void _peer2;
            if (strArray[2].equals("big")) {
                inputFile = new TLInputFileBig();
                inputFile.setId(Long.parseLong(strArray[3]));
                inputFile.setName(strArray[1]);
                inputFile.setParts(Integer.parseInt(strArray[4]));
            } else {
                inputFile = new TLInputFile();
                inputFile.setId(Long.parseLong(strArray[3]));
                inputFile.setName(strArray[1]);
                inputFile.setParts(Integer.parseInt(strArray[4]));
            }
            String media_caption = "";
            if (strArray.length >= 7) {
                media_caption = strArray[6];
            }
            String extension = "";
            int i = strArray[1].lastIndexOf(46);
            if (i <= 0) return "Err:";
            extension = strArray[1].substring(i + 1);
            TLAbsInputMedia _media = null;
            if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("png")) {
                _media = new TLInputMediaUploadedPhoto();
                _media.setCaption(media_caption);
                _media.setFile(inputFile);
            } else {
                _media = new TLInputMediaUploadedDocument();
                TLVector<TLAbsDocumentAttribute> attributes = new TLVector<TLAbsDocumentAttribute>();
                TLDocumentAttributeFilename fileNam = new TLDocumentAttributeFilename();
                fileNam.setFileName(strArray[1]);
                attributes.add(fileNam);
                ((TLInputMediaUploadedDocument)_media).setCaption(media_caption);
                this.logWithSenderNumber(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
                ((TLInputMediaUploadedDocument)_media).setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
                ((TLInputMediaUploadedDocument)_media).setFile(inputFile);
                ((TLInputMediaUploadedDocument)_media).setAttributes(attributes);
            }
            _media.serialize();
            TLRequestMessagesSendMedia request = new TLRequestMessagesSendMedia();
            request.computeFlags(true, false, false);
            request.setPeer((TLAbsInputPeer)_peer2);
            request.setMedia(_media);
            request.setRandomId(this.rnd.nextLong());
            this.logWithSenderNumber("Sending file...");
            TLUpdates tlUpdates = (TLUpdates)this.api.doRpcCall(request);
            this.logWithSenderNumber("File Sent.");
            return "File Sent.";
        }
        catch (Exception ex) {
            return "Err:" + ex.getMessage();
        }
    }

    public String uploadFile(String FileAddress, String caption) throws IOException {
        if (!new File(FileAddress).exists()) {
            System.out.println("File not found. :|");
            return "File not found";
        }
        try {
            try {
                if (caption == null || caption == "null" || caption.isEmpty()) {
                    caption = "";
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            String file_name = FileAddress.substring(FileAddress.lastIndexOf(92) + 1);
            System.out.println("Uploading " + file_name + "...");
            int task = this.api.getUploader().requestTask(FileAddress, null);
            this.api.getUploader().waitForTask(task);
            int resultState = this.api.getUploader().getTaskState(task);
            Uploader.UploadResult result = this.api.getUploader().getUploadResult(task);
            String files = "uploaded;" + file_name + ";" + (result.isUsedBigFile() ? "big" : "small") + ";" + result.getFileId() + ";" + result.getPartsCount() + ";" + result.getHash() + ";" + caption;
            System.out.println("Uploaded.");
            return files;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Err: " + e.getMessage();
        }
    }

    private String sendFile(int userId, String file, String chat_type) throws IOException {
        return this.sendFile(userId, 0L, file, chat_type);
    }

    private String sendFile(int userId, long access_hash, String file, String chat_type) throws IOException {
        try {
            TLAbsInputPeer peer;
            String[] file_array = file.split(";");
            if (!file_array[0].equals("uploaded")) {
                System.out.println("File not uploaded!. :|");
                return "File Not Uploaded";
            }
            if (chat_type == "group") {
                peer = new TLInputPeerChat();
                peer.setChatId(userId);
            } else if (chat_type == "channel") {
                peer = new TLInputPeerChannel();
                ((TLInputPeerChannel)peer).setAccessHash(access_hash);
                ((TLInputPeerChannel)peer).setChannelId(userId);
            } else {
                peer = new TLInputPeerUser();
                ((TLInputPeerUser)peer).setUserId(userId);
            }
            try {
                int i;
                String ext = "";
                String media_caption = "";
                if (file_array.length >= 7) {
                    media_caption = file_array[6];
                }
                if ((i = file_array[1].lastIndexOf(46)) <= 0) {
                    return "Invalid file";
                }
                ext = file_array[1].substring(i + 1).toLowerCase();
                if (file_array[2].equals("big")) {
                    TLInputFileBig inputFile = new TLInputFileBig();
                    inputFile.setId(Long.parseLong(file_array[3]));
                    inputFile.setName(file_array[1]);
                    inputFile.setParts(Integer.parseInt(file_array[4]));
                } else {
                    TLInputFile inputFile = new TLInputFile();
                    inputFile.setId(Long.parseLong(file_array[3]));
                    inputFile.setName(file_array[1]);
                    inputFile.setParts(Integer.parseInt(file_array[4]));
                }
                TLAbsInputMedia sendingMedia = null;
                if (ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
                    sendingMedia = new TLInputMediaUploadedPhoto();
                } else if (!(ext.equals("mp3") || ext.equals("wav") || ext.equals("mov") || ext.equals("avi") || ext.equals("mp4") || ext.equals("3gp"))) {
                    sendingMedia = ext.equals("pdf") ? new TLInputMediaUploadedDocument() : new TLInputMediaUploadedDocument();
                }
                sendingMedia.serialize();
                System.out.println("Sending file...");
                TLRequestMessagesSendMedia rmss = new TLRequestMessagesSendMedia();
                rmss.setMedia(sendingMedia);
                rmss.setPeer(peer);
                rmss.setRandomId(this.rnd.nextLong());
                this.api.doRpcCall(rmss, null);
            }
            catch (Exception e) {
                System.out.println("Error in send file.");
                e.printStackTrace();
                return "Err: " + e.getMessage();
            }
            System.out.println("File Sent.");
            return "File Sent";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Err: " + e.getMessage();
        }
    }

    public TLAbsDialogs getChannels() {
        TLAbsDialogs tlAbsDialogs = null;
        try {
            this.logWithSenderNumber("Fetching channels...");
            TLRequestChannelsGetDialogs cgd = new TLRequestChannelsGetDialogs();
            cgd.setLimit(Integer.MAX_VALUE);
            cgd.setOffset(0);
            tlAbsDialogs = (TLAbsDialogs)this.api.doRpcCall(cgd);
            this.logWithSenderNumber("Channels fetched.");
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return null;
        }
        return tlAbsDialogs;
    }

    public TLAbsDialogs getChatDialogs() {
        TLAbsDialogs tlAbsDialogs = null;
        try {
            TLRequestMessagesGetDialogs cgd = new TLRequestMessagesGetDialogs();
            cgd.setLimit(Integer.MAX_VALUE);
            cgd.setOffsetDate(0);
            cgd.setOffsetId(0);
            cgd.setOffsetPeer(new TLInputPeerEmpty());
            tlAbsDialogs = (TLAbsDialogs)this.api.doRpcCall(cgd);
            this.logWithSenderNumber("Groups fetched.");
        }
        catch (Exception ex) {
            this.logWithSenderNumber(ex.getMessage());
            return null;
        }
        return tlAbsDialogs;
    }

    public String getBotDialogs() {
        String msg = "";
        TLMessagesSlice tlAbsDialogs = null;
        try {
            TLRequestMessagesGetHistory h = new TLRequestMessagesGetHistory();
            h.setAddOffset(0);
            h.setLimit(1);
            h.setMaxId(0);
            h.setMinId(0);
            h.setOffsetDate(0);
            h.setOffsetId(0);
            TLInputPeerUser b = new TLInputPeerUser();
            b.setUserId(this.bot.getId());
            b.setAccessHash(this.bot.getAccessHash());
            h.setPeer(b);
            tlAbsDialogs = (TLMessagesSlice)this.api.doRpcCall(h);
            this.logWithSenderNumber("bot fetched.");
            for (TLAbsMessage message : tlAbsDialogs.getMessages()) {
                if (!(message instanceof TLMessage)) continue;
                msg = this.last_message = ((TLMessage)message).getMessage();
                break;
            }
            this.logWithSenderNumber("botmsg:" + this.last_message);
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return "Err.";
        }
        return msg;
    }

    public void join_supergroup(String link, String[] numbers) {
        block9 : {
            try {
                TLUpdates result;
                TLObject ch;
                TLChannel chh = null;
                this.logWithSenderNumber("start join..");
                String[] split = link.split("/");
                if (!link.toLowerCase().contains("joinchat")) break block9;
                try {
                    TLRequestMessagesCheckChatInvite tlch = new TLRequestMessagesCheckChatInvite();
                    tlch.setHash(split[split.length - 1]);
                    TLChatInviteAlready y = (TLChatInviteAlready)this.api.doRpcCall(tlch);
                    chh = (TLChannel)y.getChat();
                }
                catch (Exception e) {
                    ch = new TLRequestMessagesImportChatInvite();
                    ch.setHash(split[split.length - 1]);
                    result = (TLUpdates)this.api.doRpcCall(ch);
                    chh = (TLChannel)result.getChats().get(0);
                }
                TLRequestChannelsInviteToChannel chi = new TLRequestChannelsInviteToChannel();
                ch = new TLInputChannel();
                ch.setChannelId(chh.getId());
                ch.setAccessHash(chh.getAccessHash());
                chi.setChannel((TLAbsInputChannel)ch);
                try {
                    TLImportedContacts importedContacts = this.importContacts(numbers);
                    TLVector<TLAbsUser> users = importedContacts.getUsers();
                    TLVector<TLAbsInputUser> broadcast_list = new TLVector<TLAbsInputUser>();
                    for (int i = 0; i < users.size(); ++i) {
                        TLUser tlAbsUser = (TLUser)users.get(i);
                        TLInputUser uc = new TLInputUser();
                        uc.setUserId(tlAbsUser.getId());
                        uc.setAccessHash(tlAbsUser.getAccessHash());
                        this.logWithSenderNumber("ui" + tlAbsUser.getId() + " h:" + tlAbsUser.getAccessHash());
                        broadcast_list.add(uc);
                    }
                    chi.setUsers(broadcast_list);
                }
                catch (Exception e) {
                    this.logWithSenderNumber("Err. on import" + e.getMessage());
                }
                try {
                    result = (TLUpdates)this.api.doRpcCall(chi);
                    this.logWithSenderNumber("joined!");
                }
                catch (RpcException rx) {
                    this.logWithSenderNumber("Err." + rx.getErrorTag());
                }
            }
            catch (Exception e) {
                this.logWithSenderNumber("Err." + e.getMessage());
            }
        }
    }

    public TLIntVector getChannelParticipants() throws IOException {
        TLIntVector users = new TLIntVector();
        try {
            TLRequestChannelsGetParticipants tgc = new TLRequestChannelsGetParticipants();
            TLInputChannel ch = new TLInputChannel();
            ch.setAccessHash(this.channel.getAccessHash());
            ch.setChannelId(this.channel.getId());
            tgc.setChannel(ch);
            tgc.setFilter(new TLChannelParticipantsFilterRecent());
            TLChannelParticipants participants = (TLChannelParticipants)this.api.doRpcCall(tgc);
            for (int i = 0; i < participants.getParticipants().size(); ++i) {
                TLAbsChannelParticipant cp = participants.getParticipants().get(i);
                users.add(cp.getUserId());
            }
            return users;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new TLIntVector();
        }
    }

    public TLIntVector InviteCurrentMembersToChannel() {
        try {
            TLVector<TLAbsInputUser> broadcast_list = new TLVector<TLAbsInputUser>();
            for (int i = 0; i < this.extractedIds.size(); ++i) {
                TLInputUser uc = new TLInputUser();
                uc.setUserId((Integer)this.extractedIds.get(i));
                broadcast_list.add(uc);
            }
            int[] ids = new int[this.extractedIds.size()];
            for (int i = 0; i < this.extractedIds.size(); ++i) {
                ids[i] = (Integer)this.extractedIds.get(i);
            }
            TLIntVector added_users = new TLIntVector();
            TLRequestChannelsInviteToChannel ich = new TLRequestChannelsInviteToChannel();
            TLInputChannel ic = new TLInputChannel();
            ic.setAccessHash(this.channel.getAccessHash());
            ic.setChannelId(this.channel.getId());
            ich.setChannel(ic);
            ich.setUsers(broadcast_list);
            TLUpdates createResult = (TLUpdates)this.api.doRpcCall(ich);
            TLIntVector all_users = this.getChannelParticipants();
            for (int i = 0; i < all_users.size(); ++i) {
                if (!ArrayUtils.contains(ids, (Integer)all_users.get(i))) continue;
                added_users.add((Integer)all_users.get(i));
            }
            this.logWithSenderNumber("channel participenr size :" + added_users.size());
            return added_users;
        }
        catch (Exception e) {
            this.logWithSenderNumber("Err." + e.getMessage());
            return null;
        }
    }

    public boolean deleteChannel(TLInputChannel _channel) {
        try {
            TLRequestChannelsDeleteChannel chd = new TLRequestChannelsDeleteChannel();
            chd.setChannel(_channel);
            this.api.doRpcCall(chd);
            this.logWithSenderNumber("Channel deleted.");
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public String[] extractMembers(String[] numbers) {
        String[] strArray1 = null;
        try {
            TLVector<TLAbsUser> users = this.importContacts(numbers).getUsers();
            strArray1 = new String[users.size()];
            for (int i = 0; i < users.size(); ++i) {
                if (users.get(i) instanceof TLUser) {
                    strArray1[i] = ((TLUser)users.get(i)).getPhone();
                }
                if (!(users.get(i) instanceof TLInputUserSelf)) continue;
                strArray1[i] = ((TLInputUserSelf)((Object)users.get(i))).toString();
            }
        }
        catch (Exception users) {
            // empty catch block
        }
        return strArray1;
    }

    public String extractMembers(TLVector<TLAbsUser> numbers) {
        try {
            TLImportedContacts result = new TLImportedContacts();
            result.setUsers(numbers);
            TLVector<TLAbsUser> contacts = result.getUsers();
            this.extractedNumbers = new TLStringVector();
            this.extractedIds = new TLIntVector();
            for (int i = 0; i < contacts.size(); ++i) {
                this.extractedNumbers.add("" + contacts.get(i).getId() + "");
                this.extractedIds.add(contacts.get(i).getId());
            }
            System.out.println("size: " + contacts.size());
            return "imported";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String checkIsRestricted(int id) {
        try {
            TLVector<TLAbsInputUser> _users = new TLVector<TLAbsInputUser>();
            TLInputUser us = new TLInputUser();
            us.setUserId(id);
            _users.add(us);
            TLInputChannel chh = new TLInputChannel();
            chh.setAccessHash(this.channel.getAccessHash());
            chh.setChannelId(this.channel.getId());
            TLRequestChannelsInviteToChannel chi = new TLRequestChannelsInviteToChannel();
            chi.setChannel(chh);
            chi.setUsers(_users);
            TLUpdates tlUpdates = (TLUpdates)this.api.doRpcCall(chi);
            return "added";
        }
        catch (Exception ex) {
            return "timeout";
        }
    }

    public String[] extractMembersWithId(TLStringVector numbers) {
        String[] numbers1 = new String[numbers.size()];
        for (int i = 0; i < numbers.size(); ++i) {
            numbers1[i] = (String)numbers.get(i);
        }
        return numbers1;
    }

    public String[] getExtractedNumbers() {
        String[] strArray = new String[this.extractedNumbers.size()];
        for (int i = 0; i < this.extractedNumbers.size(); ++i) {
            strArray[i] = (String)this.extractedNumbers.get(i);
        }
        return strArray;
    }

    public int[] getExtractedIds() {
        int[] numArray = new int[this.extractedIds.size()];
        for (int i = 0; i < this.extractedIds.size(); ++i) {
            numArray[i] = (Integer)this.extractedIds.get(i);
        }
        return numArray;
    }

    public String checkIsRestrictedForGroup(int id) {
        try {
            TLRequestMessagesAddChatUser cu = new TLRequestMessagesAddChatUser();
            cu.setChatId(this.chatId);
            TLInputUser ip = new TLInputUser();
            ip.setUserId(id);
            cu.setUserId(ip);
            TLUpdates tlUpdates = (TLUpdates)this.api.doRpcCall(cu);
            return "added";
        }
        catch (Exception ex) {
            return "timeout";
        }
    }

    public String sendMessageToChannel(TLVector<TLAbsUser> channelMembers) throws IOException {
        String FullName = "ddd";
        String FirstName = "";
        String LastName = "";
        File file = new File(JarPath.GetPath() + "\\Profile1.txt");
        FullName = this.readFile(JarPath.GetPath() + "\\Profile1.txt");
        if (this.Message == null) {
            this.Message = this.readFile(JarPath.GetPath() + "\\message.txt");
        }
        System.out.print("Sending from " + this.SenderPhoneNo + " to " + channelMembers.size() + " Persons...");
        try {
            if (this.extractMembers(channelMembers) == null) {
                throw new Exception("Import error..");
            }
            for (int i = 0; i < channelMembers.size(); ++i) {
                if (this.extractedNumbers.contains(channelMembers.get(i))) continue;
            }
            if (this.createChannel(FullName, FullName) != "created") {
                throw new Exception("Create channel error..");
            }
            try {
                if (new File(JarPath.GetPath() + "\\group.jpg").exists()) {
                    this.changeChannelPhoto(this.uploadProfilePic(JarPath.GetPath() + "\\group.jpg"));
                }
            }
            catch (Exception e) {
                System.out.println("Change channel avatar error... " + e.getMessage());
            }
            TLIntVector invitedMembers = this.InviteCurrentMembersToChannel();
            int invited = 0;
            for (int i = 0; i < this.extractedIds.size(); ++i) {
                if (!invitedMembers.contains(this.extractedIds.get(i))) continue;
                ++invited;
            }
            TLRequestMessagesSendMessage rms = new TLRequestMessagesSendMessage();
            TLInputPeerChannel pee = new TLInputPeerChannel();
            pee.setAccessHash(this.channel.getAccessHash());
            pee.setChannelId(this.channel.getId());
            rms.setPeer(pee);
            rms.setMessage(this.Message);
            rms.setRandomId(this.rnd.nextInt());
            TLVector<TLAbsMessageEntity> ent = new TLVector<TLAbsMessageEntity>();
            TLMessageEntityBold bol = new TLMessageEntityBold();
            bol.setLength(2);
            bol.setOffset(2);
            ent.add(bol);
            rms.setEntities(ent);
            rms.enableBroadcast(true);
            this.api.doRpcCall(rms);
        }
        catch (Exception e) {
            if (e.getMessage().contains("FLOOD_WAIT") || e.getMessage().toLowerCase().contains("peer_flood")) {
                System.out.println("\tFLOOD_WAIT.");
                return "FLOOD_WAIT";
            }
            e.printStackTrace();
            return e.getMessage();
        }
        System.out.println("\tSent.");
        return "Sent.";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean deleteAllChannels() {
        TLAbsDialogs channels = null;
        try {
            channels = this.getChannels();
            this.logWithSenderNumber("Deleting channels...");
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (channels == null) {
            return false;
        }
        int i = 0;
        do {
            try {
                while (i++ < channels.getChats().size()) {
                    TLChannel cc = (TLChannel)channels.getChats().get(i);
                    TLRequestChannelsDeleteChannel chd = new TLRequestChannelsDeleteChannel();
                    TLInputChannel ic = new TLInputChannel();
                    ic.setAccessHash(cc.getAccessHash());
                    ic.setChannelId(cc.getId());
                    chd.setChannel(ic);
                    try {
                        this.api.doRpcCall(chd);
                    }
                    catch (RpcException e) {
                        this.logWithSenderNumber(e.getErrorTag());
                        return false;
                    }
                    catch (IOException ex) {
                        return false;
                    }
                    catch (TimeoutException ex) {
                        return false;
                        return true;
                    }
                }
            }
            catch (Exception e) {
                this.logWithSenderNumber(e.getMessage());
                continue;
            }
            break;
        } while (true);
    }

    public void deleteChannels() {
        TLAbsDialogs channels = this.getChannels();
        for (int i = 0; i < channels.getDialogs().size(); ++i) {
            try {
                TLChannel tlChannel = (TLChannel)channels.getChats().get(i);
                TLInputChannel ic = new TLInputChannel();
                ic.setAccessHash(((TLChannel)channels.getChats().get(i)).getAccessHash());
                ic.setChannelId(((TLChannel)channels.getChats().get(i)).getId());
                TLRequestChannelsDeleteChannel delet = new TLRequestChannelsDeleteChannel();
                delet.setChannel(ic);
                TLRequestChannelsLeaveChannel leav = new TLRequestChannelsLeaveChannel();
                leav.setChannel(ic);
                this.logWithSenderNumber("Deleting.... ");
                try {
                    this.api.doRpcCall(delet);
                    this.api.doRpcCall(leav);
                }
                catch (Exception e) {
                    this.api.doRpcCall(leav);
                }
                continue;
            }
            catch (Exception ex) {
                this.logWithSenderNumber("Err." + ex.getMessage());
            }
        }
    }

    public boolean resolveChannelUserName(String username) {
        try {
            this.logWithSenderNumber("Resolving username...");
            TLRequestContactsResolveUsername res = new TLRequestContactsResolveUsername();
            res.setUsername(username);
            TLResolvedPeer tlResolvedPeer = (TLResolvedPeer)this.api.doRpcCallNonAuth(res);
            this.logWithSenderNumber("Username resolved!");
            if (tlResolvedPeer.getChats().size() > 0) {
                TLAbsChat tlAbsChat = tlResolvedPeer.getChats().get(0);
                if (!(tlAbsChat instanceof TLChannel)) {
                    return false;
                }
                this.channel = (TLChannel)tlAbsChat;
                return true;
            }
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return false;
        }
        return false;
    }

    public void deleteGroupMember(boolean exitself) {
        this.logWithSenderNumber("Deleting..");
        try {
            TLRequestMessagesDeleteChatUser mdc = new TLRequestMessagesDeleteChatUser();
            mdc.setChatId(this.chatId);
            TLRequestMessagesGetFullChat gf = new TLRequestMessagesGetFullChat();
            TLMessagesChatFull fl = null;
            gf.setChatId(this.chatId);
            try {
                fl = (TLMessagesChatFull)this.api.doRpcCall(gf);
            }
            catch (Exception e) {
                this.logWithSenderNumber("Err." + e.getMessage());
            }
            TLInputUser uss = new TLInputUser();
            TLRequestUsersGetFullUser ug = new TLRequestUsersGetFullUser();
            ug.setId(new TLInputUserSelf());
            TLUserFull im = (TLUserFull)this.api.doRpcCall(ug);
            for (int j = 0; j < fl.getUsers().size(); ++j) {
                if (im.getUser().getId() == fl.getUsers().get(j).getId()) continue;
                try {
                    uss.setUserId(fl.getUsers().get(j).getId());
                    mdc.setUserId(uss);
                    this.api.doRpcCall(mdc);
                    continue;
                }
                catch (Exception e) {
                    this.logWithSenderNumber("Err." + e.getMessage());
                }
            }
            if (exitself) {
                uss.setUserId(im.getUser().getId());
                mdc.setUserId(uss);
                this.api.doRpcCall(mdc);
            }
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
        }
        this.logWithSenderNumber("Finished.");
    }

    public void leaveGroup() {
        this.logWithSenderNumber("Deleting..");
        try {
            TLRequestMessagesDeleteChatUser mdc = new TLRequestMessagesDeleteChatUser();
            mdc.setChatId(this.chatId);
            TLInputUser uss = new TLInputUser();
            TLRequestUsersGetFullUser ug = new TLRequestUsersGetFullUser();
            ug.setId(new TLInputUserSelf());
            TLUserFull im = (TLUserFull)this.api.doRpcCall(ug);
            uss.setUserId(im.getUser().getId());
            mdc.setUserId(uss);
            this.api.doRpcCall(mdc);
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
        }
        this.logWithSenderNumber("Finished.");
    }

    public void deleteGroups() {
        this.logWithSenderNumber("Deleting..");
        TLAbsDialogs chatDialogs = this.getChatDialogs();
        for (int i = 0; i < chatDialogs.getChats().size(); ++i) {
            if (!(chatDialogs.getChats().get(i) instanceof TLChannel)) {
                try {
                    TLAbsChat tlAbsChat = chatDialogs.getChats().get(i);
                    TLRequestMessagesDeleteHistory dh = new TLRequestMessagesDeleteHistory();
                    TLInputPeerChat ipc = new TLInputPeerChat();
                    ipc.setChatId(tlAbsChat.getId());
                    dh.setPeer(ipc);
                    dh.setMaxId(Integer.MAX_VALUE);
                    TLRequestMessagesDeleteChatUser mdc = new TLRequestMessagesDeleteChatUser();
                    mdc.setChatId(tlAbsChat.getId());
                    TLRequestMessagesGetFullChat gf = new TLRequestMessagesGetFullChat();
                    TLMessagesChatFull fl = null;
                    gf.setChatId(tlAbsChat.getId());
                    try {
                        fl = (TLMessagesChatFull)this.api.doRpcCall(gf);
                    }
                    catch (Exception e) {
                        this.logWithSenderNumber(e.getMessage());
                    }
                    TLInputUser uss = new TLInputUser();
                    TLRequestUsersGetFullUser ug = new TLRequestUsersGetFullUser();
                    ug.setId(new TLInputUserSelf());
                    TLUserFull im = (TLUserFull)this.api.doRpcCall(ug);
                    for (int j = 0; j < fl.getUsers().size(); ++j) {
                        if (im.getUser().getId() == fl.getUsers().get(j).getId()) continue;
                        try {
                            uss.setUserId(fl.getUsers().get(j).getId());
                            mdc.setUserId(uss);
                            this.api.doRpcCall(mdc);
                            continue;
                        }
                        catch (Exception e) {
                            this.logWithSenderNumber(e.getMessage());
                        }
                    }
                    uss.setUserId(im.getUser().getId());
                    mdc.setUserId(uss);
                    this.api.doRpcCall(mdc);
                    try {
                        this.api.doRpcCall(dh);
                    }
                    catch (RpcException e) {
                        this.logWithSenderNumber(e.getErrorTag());
                    }
                }
                catch (Exception tlAbsChat) {
                    // empty catch block
                }
            }
            this.logWithSenderNumber("Finished.");
        }
    }

    public boolean deleteAllGroups() {
        try {
            TLAbsDialogs chatDialogs = this.getChatDialogs();
            this.logWithSenderNumber("Deleting groups...");
            int i = 0;
            while (i++ < chatDialogs.getChats().size()) {
                try {
                    TLInputPeerChat peer = new TLInputPeerChat();
                    peer.setChatId(chatDialogs.getChats().get(i).getId());
                    TLRequestMessagesDeleteHistory mdh = new TLRequestMessagesDeleteHistory();
                    mdh.setMaxId(Integer.MAX_VALUE);
                    mdh.setPeer(peer);
                    this.api.doRpcCall(mdh);
                }
                catch (Exception ex) {
                    return false;
                }
            }
            return true;
        }
        catch (Exception ex) {
            this.logWithSenderNumber(ex.getMessage());
            return false;
        }
    }

    public boolean joinChannel(int id, long access_hash) {
        try {
            this.logWithSenderNumber("join channel...");
            TLRequestChannelsJoinChannel cch = new TLRequestChannelsJoinChannel();
            TLInputChannel peer = new TLInputChannel();
            peer.setAccessHash(access_hash);
            peer.setChannelId(id);
            cch.setChannel(peer);
            TLUpdates up = (TLUpdates)this.api.doRpcCall(cch);
            this.logWithSenderNumber("joined.");
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean leaveChannel(int id, long access_hash) {
        try {
            this.logWithSenderNumber("Leaving channel...");
            TLRequestChannelsLeaveChannel fch = new TLRequestChannelsLeaveChannel();
            TLInputChannel peer = new TLInputChannel();
            peer.setAccessHash(access_hash);
            peer.setChannelId(id);
            fch.setChannel(peer);
            TLAbsUpdates up = (TLAbsUpdates)this.api.doRpcCall(fch);
            this.logWithSenderNumber("leaved!");
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean SetUsername(String username) {
        try {
            this.logWithSenderNumber("check username...");
            TLRequestAccountCheckUsername chk = new TLRequestAccountCheckUsername();
            chk.setUserName(username);
            TLBool result = (TLBool)this.api.doRpcCall(chk);
            if (result instanceof TLBoolTrue) {
                this.logWithSenderNumber("username avaible.");
                TLRequestAccountUpdateUsername uss = new TLRequestAccountUpdateUsername();
                uss.setUserName(username);
                TLAbsUser im = (TLAbsUser)this.api.doRpcCall(uss);
                return true;
            }
            this.logWithSenderNumber("username not avaible.");
            return false;
        }
        catch (Exception e) {
            System.out.println("problem updating username.");
            return false;
        }
    }

    public TLAbsChat resolveUserName(String username) {
        try {
            TLRequestContactsResolveUsername ru = new TLRequestContactsResolveUsername();
            ru.setUsername(username);
            TLResolvedPeer tlResolvedPeer = (TLResolvedPeer)this.api.doRpcCall(ru);
            if (tlResolvedPeer.getChats().size() > 0) {
                return tlResolvedPeer.getChats().get(0);
            }
            if (tlResolvedPeer.getUsers().size() > 0) {
                TLAbsUser tlabsuser = tlResolvedPeer.getUsers().get(0);
                if (!(tlabsuser instanceof TLUser)) {
                    return null;
                }
                this.bot = (TLUser)tlabsuser;
                return null;
            }
        }
        catch (Exception ex) {
            return null;
        }
        return null;
    }

    public TLInputPeerChannel joinChannel(String username) {
        try {
            TLAbsChat tlAbsChat = this.resolveUserName(username);
            if (tlAbsChat instanceof TLChannel) {
                TLChannel tch = (TLChannel)tlAbsChat;
                this.logWithSenderNumber("Joining...");
                TLInputChannel chh = new TLInputChannel();
                chh.setAccessHash(tch.getAccessHash());
                chh.setChannelId(tch.getId());
                TLRequestChannelsJoinChannel jch = new TLRequestChannelsJoinChannel();
                jch.setChannel(chh);
                this.api.doRpcCall(jch);
                this.logWithSenderNumber("Joined!");
                TLInputPeerChannel peer = new TLInputPeerChannel();
                peer.setAccessHash(tch.getAccessHash());
                peer.setChannelId(tch.getId());
                return peer;
            }
            this.logWithSenderNumber("Is not a channel");
            return null;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return null;
        }
    }

    public boolean markChannelMessagesAsRead(String username) {
        try {
            TLInputPeerChannel inputPeerChannel = this.joinChannel(username);
            if (inputPeerChannel != null) {
                TLRequestMessagesGetHistory rmg = new TLRequestMessagesGetHistory();
                rmg.setPeer(inputPeerChannel);
                rmg.setAddOffset(0);
                rmg.setLimit(0);
                rmg.setMaxId(0);
                rmg.setMinId(0);
                rmg.setOffsetDate(0);
                rmg.setOffsetId(0);
                TLAbsMessages dialogs = (TLAbsMessages)this.api.doRpcCall(rmg);
                TLVector<TLAbsMessage> messages = dialogs.getMessages();
                TLIntVector _id = new TLIntVector();
                for (int i = 0; i < messages.size(); ++i) {
                    if (!(messages.get(i) instanceof TLMessage)) continue;
                    _id.add(((TLMessage)messages.get(i)).getId());
                }
                TLRequestMessagesGetMessagesViews inc_view = new TLRequestMessagesGetMessagesViews();
                inc_view.setId(_id);
                inc_view.setIncrement(true);
                inc_view.setPeer(inputPeerChannel);
                TLIntVector tlIntVector = (TLIntVector)this.api.doRpcCall(inc_view);
                return true;
            }
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }

    public TLIntVector getChannelMessages(int id, long access_hash) {
        try {
            TLInputChannel inputPeerChannel = new TLInputChannel();
            inputPeerChannel.setAccessHash(access_hash);
            inputPeerChannel.setChannelId(id);
            if (inputPeerChannel != null) {
                this.logWithSenderNumber("Getting messages...");
                TLRequestChannelsGetImportantHistory his = new TLRequestChannelsGetImportantHistory();
                his.setChannel(inputPeerChannel);
                his.setMaxId(35000);
                TLAbsMessages dialogs = (TLAbsMessages)this.api.doRpcCall(his);
                TLVector<TLAbsMessage> messages = dialogs.getMessages();
                TLIntVector tlIntVector = new TLIntVector();
                for (int i = 0; i < messages.size(); ++i) {
                    if (!(messages.get(i) instanceof TLMessage)) continue;
                    tlIntVector.add(((TLMessage)messages.get(i)).getId());
                }
                this.logWithSenderNumber("Messages count: " + tlIntVector.size());
                return tlIntVector;
            }
        }
        catch (Exception ex) {
            return null;
        }
        return null;
    }

    public boolean markChannelMessagesAsRead(int id, long access_hash, TLIntVector ids) {
        try {
            TLInputPeerChannel inputPeerChannel = new TLInputPeerChannel();
            inputPeerChannel.setAccessHash(access_hash);
            inputPeerChannel.setChannelId(id);
            if (inputPeerChannel != null) {
                this.logWithSenderNumber("Marking as read...");
                TLRequestMessagesGetMessagesViews inc_view = new TLRequestMessagesGetMessagesViews();
                inc_view.setId(ids);
                inc_view.setIncrement(true);
                inc_view.setPeer(inputPeerChannel);
                TLIntVector tlIntVector = (TLIntVector)this.api.doRpcCall(inc_view);
                for (int i = 0; i < tlIntVector.size(); ++i) {
                    System.out.println(tlIntVector.get(i));
                }
                this.logWithSenderNumber("Marked!");
                return true;
            }
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }

    public void updateChannelUsername(TLInputChannel channel, String username) throws IOException, TimeoutException {
        TLRequestChannelsUpdateUsername chu = new TLRequestChannelsUpdateUsername();
        chu.setChannel(channel);
        chu.setUsername(username);
        TLBool flag = (TLBool)this.api.doRpcCall(chu);
        if (flag instanceof TLBoolTrue) {
            System.out.println("Channel username updated.");
        } else {
            System.out.println("this username is not available!!!");
        }
    }

    public String supergroup(String[] numbers, String title) {
        try {
            TLImportedContacts importedContacts = this.importContacts(numbers);
            TLRequestMessagesCreateChat mcc = new TLRequestMessagesCreateChat();
            TLVector<TLAbsInputUser> usrs = new TLVector<TLAbsInputUser>();
            for (int i = 0; i < importedContacts.getUsers().size(); ++i) {
                if (!(importedContacts.getUsers().get(i) instanceof TLUser)) continue;
                TLUser user = (TLUser)importedContacts.getUsers().get(i);
                TLInputUser t = new TLInputUser();
                t.setUserId(user.getId());
                usrs.add(t);
            }
            mcc.setUsers(usrs);
            mcc.setTitle(title);
            TLUpdates tlUpdates = (TLUpdates)this.api.doRpcCall(mcc);
            TLVector<TLAbsUser> users = tlUpdates.getUsers();
            TLStringVector tlStringVector = new TLStringVector();
            this.us.clear();
            for (int i = 0; i < users.size(); ++i) {
                if (!(users.get(i) instanceof TLUser)) continue;
                TLUser user = (TLUser)users.get(i);
                TLInputUser t = new TLInputUser();
                t.setAccessHash(user.getAccessHash());
                t.setUserId(user.getId());
                this.us.add(t);
            }
            this.chatId = tlUpdates.getChats().get(0).getId();
        }
        catch (RpcException e) {
            this.logWithSenderNumber("Err." + e.getErrorTag());
            return "Err." + e.getErrorTag();
        }
        catch (Exception ee) {
            this.logWithSenderNumber(ee.getMessage());
            return "Err." + ee.getMessage();
        }
        return "Created.";
    }

    public void deleteSuperGroupMembers() {
        TLUserFull im = null;
        try {
            TLInputUser uss = new TLInputUser();
            TLRequestUsersGetFullUser ug = new TLRequestUsersGetFullUser();
            ug.setId(new TLInputUserSelf());
            im = (TLUserFull)this.api.doRpcCall(ug);
        }
        catch (Exception e) {
            this.logWithSenderNumber("Err." + e.getMessage());
        }
        for (int i = 0; i < this.us.size(); ++i) {
            try {
                if (im.getUser().getId() == ((TLInputUser)this.us.get(i)).getUserId()) continue;
                TLRequestChannelsKickFromChannel chd = new TLRequestChannelsKickFromChannel();
                TLInputChannel c = new TLInputChannel();
                c.setAccessHash(this.chathash);
                c.setChannelId(this.chatId);
                chd.setChannel(c);
                chd.setKicked(true);
                chd.setUserId((TLAbsInputUser)this.us.get(i));
                TLAbsUpdates tLAbsUpdates = (TLAbsUpdates)this.api.doRpcCall(chd);
                continue;
            }
            catch (RpcException ex) {
                System.out.print("Err." + ex.getErrorTag());
                continue;
            }
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
            catch (TimeoutException ex) {
                java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void kikchannel(int chid, long hash, int uid) {
        try {
            TLRequestChannelsKickFromChannel chd = new TLRequestChannelsKickFromChannel();
            TLInputChannel c = new TLInputChannel();
            c.setAccessHash(hash);
            c.setChannelId(chid);
            chd.setChannel(c);
            chd.setKicked(true);
            TLInputUser u = new TLInputUser();
            u.setUserId(uid);
            chd.setUserId(u);
            TLAbsUpdates tLAbsUpdates = (TLAbsUpdates)this.api.doRpcCall(chd);
        }
        catch (RpcException ex) {
            System.out.print("Err." + ex.getErrorTag());
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (TimeoutException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String extractMembersWithId(String[] numbers, int cycle_number, boolean writeToFile, int attemp) {
        try {
            this.logWithSenderNumber("Extract attemp " + attemp);
            TLStringVector tlStringVector = new TLStringVector();
            TLIntVector tlIntVector = new TLIntVector();
            TLImportedContacts importedContacts = null;
            try {
                importedContacts = this.importContacts(numbers);
            }
            catch (Exception e) {
                return "contacts_list_full";
            }
            TLVector<TLAbsUser> users = importedContacts.getUsers();
            if (cycle_number == 0) {
                this.extractedNumbers = new TLStringVector();
                this.extractedIds = new TLIntVector();
            }
            for (int i = 0; i < users.size(); ++i) {
                TLUser tlAbsUser = (TLUser)users.get(i);
                if (tlAbsUser.getPhone() == null) {
                    this.logWithSenderNumber("" + tlAbsUser.getFlags() + " " + tlAbsUser.getPhone() + " " + tlAbsUser.getId());
                    this.logWithSenderNumber("has null..................................");
                    if (attemp == 1) {
                        return this.extractMembersWithId(numbers, cycle_number, writeToFile, 2);
                    }
                    return "phones include null";
                }
                tlStringVector.add(tlAbsUser.getPhone());
                tlIntVector.add(tlAbsUser.getId());
            }
            String str2 = this.SenderPhoneNo + ":\r\n";
            for (int i = 0; i < tlStringVector.size(); ++i) {
                this.extractedNumbers.add(tlStringVector.get(i));
                this.extractedIds.add(tlIntVector.get(i));
                str2 = str2 + tlIntVector.get(i) + "\t" + (String)tlStringVector.get(i) + "\r\n";
            }
            if (str2 != "") {
                File file = new File(JarPath.GetPath() + "\\members.ini");
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    Files.write(file.toPath(), str2.getBytes("utf-8"), StandardOpenOption.APPEND);
                }
                catch (IOException ex) {
                    this.logWithSenderNumber("Err." + ex.getMessage());
                }
            }
            this.logWithSenderNumber("size: " + users.size());
            return "imported";
        }
        catch (Exception ex) {
            return "Err." + ex.getMessage();
        }
    }

    public void toggleChannelComments(String username, boolean toggle) {
        boolean num1 = toggle;
        try {
            TLChannel tlAbsChat = (TLChannel)this.resolveUserName(username);
            if (tlAbsChat instanceof TLChannel) {
                TLRequestChannelsToggleComments ctg = new TLRequestChannelsToggleComments();
                TLInputChannel ic = new TLInputChannel();
                ic.setAccessHash(tlAbsChat.getAccessHash());
                ic.setChannelId(tlAbsChat.getId());
                ctg.setChannel(ic);
                ctg.setEnabled(toggle);
                this.api.doRpcCall(ctg, null);
                return;
            }
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber(ex.getMessage());
            return;
        }
    }

    public String[] createChatWithCurrentMembers(String title) {
        String[] strArray2;
        this.logWithSenderNumber("Create group...");
        try {
            int i;
            TLRequestMessagesCreateChat mcc = new TLRequestMessagesCreateChat();
            TLVector<TLAbsInputUser> usrs = new TLVector<TLAbsInputUser>();
            for (int i2 = 0; i2 < this.extractedIds.size(); ++i2) {
                TLInputUser t = new TLInputUser();
                t.setUserId((Integer)this.extractedIds.get(i2));
                usrs.add(t);
            }
            mcc.setUsers(usrs);
            mcc.setTitle(title);
            TLUpdates tlUpdates = (TLUpdates)this.api.doRpcCall(mcc);
            TLVector<TLAbsUser> users = tlUpdates.getUsers();
            TLStringVector tlStringVector = new TLStringVector();
            for (i = 0; i < users.size(); ++i) {
                tlStringVector.add("" + users.get(i).getId() + "");
            }
            strArray2 = new String[tlStringVector.size()];
            for (i = 0; i < tlStringVector.size(); ++i) {
                strArray2[i] = (String)tlStringVector.get(i);
            }
            this.us.clear();
            for (i = 0; i < users.size(); ++i) {
                if (!(users.get(i) instanceof TLUser)) continue;
                TLUser user = (TLUser)users.get(i);
                TLInputUser t = new TLInputUser();
                t.setAccessHash(user.getAccessHash());
                t.setUserId(user.getId());
                this.us.add(t);
            }
            this.chatId = tlUpdates.getChats().get(0).getId();
            this.logWithSenderNumber("Group created. number of members: " + strArray2.length);
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return new String[]{"Err." + ex.getMessage()};
        }
        return strArray2;
    }

    public String changeChatPhoto(String avatar) {
        try {
            TLAbsInputFile fb;
            if (avatar == null) {
                return "Photo not uploaded.";
            }
            this.logWithSenderNumber("Changing Photo...");
            String[] strArray = avatar.split(";");
            TLRequestMessagesEditChatPhoto cph = new TLRequestMessagesEditChatPhoto();
            cph.setChatId(this.chatId);
            TLInputChatUploadedPhoto pho = new TLInputChatUploadedPhoto();
            if (strArray[2].equalsIgnoreCase("big")) {
                fb = new TLInputFileBig();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            } else {
                fb = new TLInputFile();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            }
            pho.setCrop(new TLInputPhotoCropAuto());
            TLInputChatUploadedPhoto aph = pho;
            cph.setPhoto(aph);
            TLUpdates ip = (TLUpdates)this.api.doRpcCall(cph);
            return "Photo changed";
        }
        catch (Exception ex) {
            return "Err." + ex.getMessage();
        }
    }

    public String changeChannelPhoto(String avatar) {
        try {
            TLAbsInputFile fb;
            if (avatar == null) {
                return "Photo not uploaded.";
            }
            String[] strArray = avatar.split(";");
            TLRequestChannelsEditPhoto cph = new TLRequestChannelsEditPhoto();
            TLInputChannel chh = new TLInputChannel();
            chh.setAccessHash(this.channel.getAccessHash());
            chh.setChannelId(this.channel.getId());
            cph.setChannel(chh);
            TLInputChatUploadedPhoto pho = new TLInputChatUploadedPhoto();
            if (strArray[2].equalsIgnoreCase("big")) {
                fb = new TLInputFileBig();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            } else {
                fb = new TLInputFile();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            }
            pho.setCrop(new TLInputPhotoCropAuto());
            TLInputChatUploadedPhoto aph = pho;
            cph.setPhoto(aph);
            this.api.doRpcCall(cph, null);
            return "Photo changed";
        }
        catch (Exception ex) {
            return "Err: " + ex.getMessage();
        }
    }

    public void removeUsersFromChat() {
        int i1 = 0;
        for (int i2 = 0; i2 < this.extractedIds.size(); ++i2) {
            try {
                TLRequestMessagesDeleteChatUser chd = new TLRequestMessagesDeleteChatUser();
                chd.setChatId(this.chatId);
                TLInputUser usr = new TLInputUser();
                usr.setUserId((Integer)this.extractedIds.get(i2));
                chd.setUserId(usr);
                this.api.doRpcCall(chd, null);
                ++i1;
                continue;
            }
            catch (Exception chd) {
                // empty catch block
            }
        }
    }

    public void addUserToChat(int id) {
        try {
            TLRequestMessagesAddChatUser rma = new TLRequestMessagesAddChatUser();
            rma.setChatId(this.chatId);
            TLInputUser usr = new TLInputUser();
            usr.setUserId(id);
            rma.setUserId(usr);
            rma.setFwdLimit(5);
            TLUpdates result = (TLUpdates)this.api.doRpcCall(rma);
            this.logWithSenderNumber("Added to chat." + this.chatId);
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("ERR." + ex.getMessage() + this.chatId);
            return;
        }
    }

    public void addUserToChat(TLUser b) {
        try {
            TLRequestMessagesAddChatUser rma = new TLRequestMessagesAddChatUser();
            rma.setChatId(this.chatId);
            TLInputUser usr = new TLInputUser();
            usr.setUserId(b.getId());
            usr.setAccessHash(b.getAccessHash());
            rma.setUserId(usr);
            rma.setFwdLimit(5);
            TLUpdates result = (TLUpdates)this.api.doRpcCall(rma);
            this.logWithSenderNumber("Added to chat.");
            return;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("ERR" + ex.getMessage());
            return;
        }
    }

    public void removeUserFromChat(int id) {
        try {
            TLRequestMessagesDeleteChatUser mdc = new TLRequestMessagesDeleteChatUser();
            mdc.setChatId(this.chatId);
            TLInputUser usr = new TLInputUser();
            usr.setUserId(id);
            mdc.setUserId(usr);
            this.api.doRpcCall(mdc, null);
            return;
        }
        catch (Exception mdc) {
            return;
        }
    }

    public String sendChangePhoneCode(String phone) throws IOException, TimeoutException {
        try {
            TLRequestAccountSendChangePhoneCode asc = new TLRequestAccountSendChangePhoneCode();
            asc.setPhoneNumber(phone);
            this.sentChangePhoneCode = (TLSentCode)this.api.doRpcCall(asc);
            this.SenderPhoneNo = phone;
            return "Code Sent";
        }
        catch (RpcException e) {
            if (e.getErrorCode() != 303 || e.getErrorTag().contains("PHONE_NUMBER_INVALID") || e.getErrorTag().contains("PHONE_CODE_EMPTY") || e.getErrorTag().contains("PHONE_CODE_INVALID") || e.getErrorTag().contains("PHONE_CODE_EXPIRED") || e.getErrorTag().startsWith("FLOOD_WAIT") || e.getErrorTag().startsWith("PHONE_NUMBER_OCCUPIED")) {
                // empty if block
            }
            System.out.print(e.getErrorTag());
            return "ERR";
        }
    }

    public String ChangePhone(String code) {
        String str = "";
        TLRequestAccountChangePhone ach = new TLRequestAccountChangePhone();
        ach.setPhoneNumber(this.SenderPhoneNo);
        ach.setPhoneCode(code);
        ach.setPhoneCodeHash(this.sentChangePhoneCode.getPhoneCodeHash());
        try {
            TLAbsUser tLAbsUser = (TLAbsUser)this.api.doRpcCall(ach);
        }
        catch (RpcException e) {
            return "Change Phone error..." + e.getErrorTag();
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
            return "Change Phone error...";
        }
        catch (TimeoutException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
            return "Change Phone error...";
        }
        try {
            this.apiState.setAuthenticated(this.apiState.getPrimaryDc(), true);
            this.saveState(this.SenderPhoneNo, this.apiState);
        }
        catch (Exception ex) {
            return "load failed!";
        }
        return "Phone Changed";
    }

    public String changeAvatar(String avatar) {
        System.out.println(avatar);
        try {
            TLAbsInputFile fb;
            if (avatar == null) {
                return "Avatar not uploaded.";
            }
            this.logWithSenderNumber("Changing avatar...");
            String[] strArray = avatar.split(";");
            TLRequestPhotosUploadProfilePhoto cph = new TLRequestPhotosUploadProfilePhoto();
            TLInputChatUploadedPhoto pho = new TLInputChatUploadedPhoto();
            if (strArray[2].equalsIgnoreCase("big")) {
                fb = new TLInputFileBig();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            } else {
                fb = new TLInputFile();
                fb.setId(Long.parseLong(strArray[3]));
                fb.setParts(Integer.parseInt(strArray[4]));
                fb.setName(strArray[1]);
                pho.setFile(fb);
            }
            pho.setCrop(new TLInputPhotoCropAuto());
            cph.setCaption("Amin!");
            cph.setGeoPoint(new TLInputGeoPointEmpty());
            cph.setCrop(new TLInputPhotoCropAuto());
            TLPhoto ph = (TLPhoto)this.api.doRpcCall(cph);
            this.logWithSenderNumber("Avatar changed.");
            return "Avatar changed";
        }
        catch (Exception e) {
            return "Err: " + e.getMessage();
        }
    }

    public String updateProfile(String FirstName, String LastName) {
        if (FirstName.isEmpty()) {
            return "Enter a name.";
        }
        this.logWithSenderNumber("Changing Profile...");
        try {
            TLRequestAccountUpdateProfile acu = new TLRequestAccountUpdateProfile();
            acu.setAbout("telep");
            acu.setFirstName(FirstName);
            acu.setLastName(LastName);
            TLUser tLUser = (TLUser)this.api.doRpcCall(acu);
        }
        catch (Exception e) {
            if (e.getMessage().contains("NAME_NOT_MODIFIED")) {
                System.out.println("Profile is Uptodate.");
            }
            e.printStackTrace();
        }
        this.logWithSenderNumber("Profile changed.");
        return "Profile changed";
    }

    public String uploadProfilePic(String FileAddress) {
        try {
            File file = new File(FileAddress);
            if (!file.exists()) {
                this.logWithSenderNumber("File not found.");
                return "File not found!";
            }
            this.logWithSenderNumber("Uploading avatar...");
            String name = file.getName();
            int taskId = this.api.getUploader().requestTask(FileAddress, null);
            this.api.getUploader().waitForTask(taskId);
            this.api.getUploader().getTaskState(taskId);
            Uploader.UploadResult uploadResult = this.api.getUploader().getUploadResult(taskId);
            String str2 = "uploaded;" + name + ";" + (!uploadResult.isUsedBigFile() ? "small" : "big") + ";" + uploadResult.getFileId() + "" + ";" + uploadResult.getPartsCount() + "" + ";" + uploadResult.getHash();
            this.logWithSenderNumber("Uploaded.");
            return str2;
        }
        catch (Exception ex) {
            return "Err: " + ex.getMessage();
        }
    }

    public int countContacts(int timeOut) {
        try {
            this.logWithSenderNumber("Counting...");
            TLRequestContactsGetContacts dd = new TLRequestContactsGetContacts();
            dd.setHash("");
            this.AccountContacts = (TLAbsContacts)this.api.doRpcCall(dd, timeOut);
            this.logWithSenderNumber("number of contacts?: " + ((TLContacts)this.AccountContacts).getUsers().size());
            return ((TLContacts)this.AccountContacts).getUsers().size();
        }
        catch (Exception ex) {
            return 0;
        }
    }

    public String clearContacts() {
        try {
            if (this.AccountContacts == null) {
                this.countContacts(25000);
            }
            if (((TLContacts)this.AccountContacts).getUsers().size() < 1) {
                return "size was 0";
            }
            TLVector<TLAbsInputUser> _id = new TLVector<TLAbsInputUser>();
            TLInputPeerUser us = new TLInputPeerUser();
            TLInputUser us2 = new TLInputUser();
            TLRequestContactsDeleteContact t = new TLRequestContactsDeleteContact();
            for (int i = 0; i < ((TLContacts)this.AccountContacts).getUsers().size(); ++i) {
                us.setUserId(((TLContacts)this.AccountContacts).getUsers().get(i).getId());
                us2.setUserId(((TLContacts)this.AccountContacts).getUsers().get(i).getId());
                t.setId(us2);
                this.api.doRpcCall(t);
                _id.add((TLAbsInputUser)((Object)us));
            }
            this.logWithSenderNumber("Clearing....");
            TLRequestContactsDeleteContacts rcd = new TLRequestContactsDeleteContacts();
            rcd.setId(_id);
            TLBool res = (TLBool)this.api.doRpcCall(rcd);
            if (res instanceof TLBoolTrue) {
                this.logWithSenderNumber("Action Complate, Contacts removed.");
            } else {
                this.logWithSenderNumber("problem");
            }
            return "End";
        }
        catch (Exception ex) {
            this.logWithSenderNumber("clearContacts:" + ex.getMessage());
            return "clearContacts:" + ex.getMessage();
        }
    }

    public String forwardMessage(String PhoneNo, int messageId) {
        TLInputPeerUser inputPeerContact = new TLInputPeerUser();
        try {
            inputPeerContact = this.addContact(PhoneNo);
        }
        catch (Exception ex) {
            return "Error in add contact! " + ex.getMessage();
        }
        try {
            TLRequestMessagesForwardMessage mf = new TLRequestMessagesForwardMessage();
            mf.setId(messageId);
            mf.setPeer(inputPeerContact);
            mf.setRandomId(this.rnd.nextLong());
            this.api.doRpcCall(mf);
            return PhoneNo + ": Sent";
        }
        catch (Exception ex) {
            return "Err." + ex.getMessage();
        }
    }

    public void deleteProfilePhotos() {
        try {
            this.logWithSenderNumber("Deleting profile photos.....");
            TLRequestPhotosUpdateProfilePhoto tpp = new TLRequestPhotosUpdateProfilePhoto();
            tpp.setId(new TLInputPhotoEmpty());
            tpp.setCrop(new TLInputPhotoCropAuto());
            while ((TLAbsUserProfilePhoto)this.api.doRpcCall(tpp) instanceof TLUserProfilePhoto) {
            }
            this.logWithSenderNumber("Photos deleted.");
            return;
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            return;
        }
        catch (Exception e) {
            this.logWithSenderNumber("Err." + e.getMessage());
            return;
        }
    }

    public void resetAuthrizations() {
        try {
            this.api.doRpcCall(new TLRequestAuthResetAuthorizations());
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public boolean setStatus(boolean offline, int timeOut) {
        try {
            TLRequestAccountUpdateStatus rau = new TLRequestAccountUpdateStatus();
            rau.setOffline(offline);
            TLBool tlBool = (TLBool)this.api.doRpcCall(rau, timeOut);
            return true;
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            return false;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return false;
        }
    }

    public String sendMessageByUid(int chat_id, String Message, String chat_type, boolean silent) {
        TLAbsInputPeer _peer = null;
        if (chat_type == "group") {
            _peer = new TLInputPeerChat();
            _peer.setChatId(this.chatId);
        } else if (chat_type == "bot") {
            if (this.bot.isBot()) {
                _peer = new TLInputPeerUser();
                ((TLInputPeerUser)_peer).setUserId(this.bot.getId());
                ((TLInputPeerUser)_peer).setAccessHash(this.bot.getAccessHash());
            }
        } else if (chat_type == "channel") {
            _peer = new TLInputPeerChannel();
            ((TLInputPeerChannel)_peer).setAccessHash(this.channel.getAccessHash());
            ((TLInputPeerChannel)_peer).setChannelId(this.channel.getId());
        } else {
            _peer = new TLInputPeerUser();
            ((TLInputPeerUser)_peer).setUserId(chat_id);
        }
        try {
            TLRequestMessagesSendMessage rms = new TLRequestMessagesSendMessage();
            rms.setMessage(Message);
            rms.setPeer(_peer);
            rms.setRandomId(this.rnd.nextLong());
            if (chat_type == "channel") {
                rms.enableBroadcast(true);
            } else {
                rms.enableBroadcast(false);
            }
            TLAbsUpdates tlAbsUpdates = (TLAbsUpdates)this.api.doRpcCall(rms);
            this.logWithSenderNumber("messsage sent.");
            if (chat_type == "bot") {
                this.getBotDialogs();
            }
            return "Sent";
        }
        catch (Exception ex) {
            return "Err: " + ex.getMessage();
        }
    }

    public String sendMessageByUid(int Uid, String Message) {
        return this.sendMessageByUid(Uid, Message, "private", false);
    }

    public boolean deleteAccount() {
        try {
            this.logWithSenderNumber("Deleting account.....");
            TLRequestAccountDeleteAccount ad = new TLRequestAccountDeleteAccount();
            ad.setReason("Forgot password");
            TLBool res = (TLBool)this.api.doRpcCallNonAuth(ad);
            this.logWithSenderNumber("Account deleted!");
            if (res instanceof TLBoolTrue) {
                return true;
            }
            return false;
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            return false;
        }
        catch (Exception y) {
            this.logWithSenderNumber("Err." + y.getMessage());
            return false;
        }
    }

    public String Authrize(String code, String first_name, String last_name) throws TimeoutException, IOException {
        return this.Authrize(code, first_name, last_name, "");
    }

    public static byte[] computeSHA256(byte[] convertme, int offset, int len) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(convertme, offset, len);
            return md.digest();
        }
        catch (Exception md) {
            return null;
        }
    }

    public static byte[] getPasswordHash(String password, TLBytes tSalt) {
        byte[] current_salt = new byte[16];
        for (int i = tSalt.getOffset(); i < tSalt.getLength(); ++i) {
            current_salt[i] = tSalt.getData()[i];
        }
        byte[] oldPasswordBytes = null;
        try {
            oldPasswordBytes = password.getBytes("UTF-8");
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
        byte[] hash = new byte[current_salt.length * 2 + oldPasswordBytes.length];
        System.arraycopy(current_salt, 0, hash, 0, current_salt.length);
        System.arraycopy(oldPasswordBytes, 0, hash, current_salt.length, oldPasswordBytes.length);
        System.arraycopy(current_salt, 0, hash, hash.length - current_salt.length, current_salt.length);
        return telegramApi.computeSHA256(hash, 0, hash.length);
    }

    public void switchAccount(String phoneNum) {
        this.SenderPhoneNo = phoneNum;
        this.extractedNumbers.clear();
        this.extractedIds.clear();
        this.AccountContacts = null;
        this.chatId = 0;
        this.channel = null;
        this.flood_wait = false;
        this.flood_time = 0;
        this.peer_flood = false;
        this.sended_files = 0;
        this.sent_result = "";
        this.sended_messages = 0;
        this.last_message = "";
        this.dcId = 2;
        this.api.state = this.loadState(phoneNum);
        this.api.switchToDc(this.api.getState().getPrimaryDc());
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }

    public String Authrize(String code, String first_name, String last_name, String password) {
        try {
            String str;
            block21 : {
                str = "";
                try {
                    if (this.sentCode.isPhoneRegistered()) {
                        TLRequestAuthSignIn sign = new TLRequestAuthSignIn();
                        sign.setPhoneCode(code);
                        sign.setPhoneCodeHash(this.sentCode.getPhoneCodeHash());
                        sign.setPhoneNumber(this.SenderPhoneNo);
                        TLAuthorization tLAuthorization = (TLAuthorization)this.api.doRpcCallNonAuth(sign);
                    } else {
                        System.out.print("signup");
                        TLRequestAuthSignUp signup = new TLRequestAuthSignUp();
                        signup.setFirstName(first_name);
                        signup.setLastName(last_name);
                        signup.setPhoneCode(code);
                        signup.setPhoneCodeHash(this.sentCode.getPhoneCodeHash());
                        signup.setPhoneNumber(this.SenderPhoneNo);
                        TLAuthorization tLAuthorization = (TLAuthorization)this.api.doRpcCallNonAuth(signup);
                    }
                }
                catch (RpcException e) {
                    if (e.getErrorTag().startsWith("PHONE_NUMBER_UNOCCUPIED")) break block21;
                    if (e.getErrorTag().contains("SESSION_PASSWORD_NEEDED")) {
                        try {
                            if (password.length() == 0) {
                                return "Err. invalid pass";
                            }
                            TLAccountPassword absAccountPassword = (TLAccountPassword)this.api.doRpcCallNonAuth(new TLRequestAccountGetPassword());
                            TLRequestAuthCheckPassword CheckPassword = new TLRequestAuthCheckPassword();
                            this.logWithSenderNumber("hint:" + absAccountPassword.getHint());
                            CheckPassword.setPasswordHash(new TLBytes(telegramApi.getPasswordHash(password, absAccountPassword.getCurrentSalt())));
                            TLAuthorization tlAuthorization = (TLAuthorization)this.api.doRpcCallNonAuth(CheckPassword);
                            str = "Login by password";
                        }
                        catch (RpcException ex) {
                            if (ex.getErrorTag().startsWith("PASSWORD_HASH_INVALID")) {
                                System.out.print("Err." + ex.getErrorTag());
                                if (this.deleteAccount()) {
                                    try {
                                        this.logWithSenderNumber("account reseted...");
                                        TLRequestAuthSignUp signup = new TLRequestAuthSignUp();
                                        signup.setFirstName(first_name + ".");
                                        signup.setLastName(last_name + ".");
                                        signup.setPhoneCode(code);
                                        signup.setPhoneCodeHash(this.sentCode.getPhoneCodeHash());
                                        signup.setPhoneNumber(this.SenderPhoneNo);
                                        TLAuthorization tlAuthorization = (TLAuthorization)this.api.doRpcCallNonAuth(signup);
                                    }
                                    catch (RpcException rx) {
                                        this.logWithSenderNumber("Err." + rx.getErrorTag());
                                    }
                                }
                                break block21;
                            }
                            return "Login failed! " + ex.getErrorTag();
                        }
                    }
                    if (e.getErrorTag().startsWith("PHONE_CODE_INVALID")) {
                        return "Login failed!" + e.getErrorTag();
                    }
                    if (e.getErrorTag().startsWith("PHONE_CODE_EXPIRED")) {
                        return "Login failed!" + e.getErrorTag();
                    }
                    if (e.getErrorTag().startsWith("PHONE_CODE_EMPTY")) {
                        return "Login failed!" + e.getErrorTag();
                    }
                    if (e.getErrorTag().startsWith("PASSWORD_HASH_INVALID")) {
                        return "Login failed! incorrect password" + e.getErrorTag();
                    }
                }
                catch (Exception e) {
                    this.logWithSenderNumber("Login failed!" + e.getMessage());
                    return "Login failed!" + e.getMessage();
                }
            }
            this.apiState.setAuthenticated(this.apiState.getPrimaryDc(), true);
            System.out.println("Activation complete.");
            System.out.print("Loading initial state...");
            try {
                this.api.doRpcCall(new TLRequestUpdatesGetState());
            }
            catch (Exception e) {
                System.out.println("err.");
                this.logWithSenderNumber("Login failed!");
                return "Login failed!";
            }
            this.saveState(this.SenderPhoneNo, this.apiState);
            this.logWithSenderNumber("stat saved.");
            System.out.println("ok");
            return str + " Login and loaded";
        }
        catch (Exception ex) {
            System.out.println("err.");
            this.logWithSenderNumber("Login failed!");
            return "Login failed!";
        }
    }

    public boolean resetAccount(String first_name, String last_name) {
        try {
            this.logWithSenderNumber("Reseting account.....");
            if (!this.setStatus(false, 10000)) {
                this.logWithSenderNumber("Cant set status.");
                return false;
            }
            this.sendMessageByUid(777000, "" + this.rnd.nextLong() + "");
            telegramApi telegramApi2 = new telegramApi(this.index);
            this.last_message = "";
            telegramApi2.login(this.SenderPhoneNo, true);
            String str = "";
            for (int index = 0; index < 5; ++index) {
                System.out.println("getting last message... attemp " + index + 1);
                Thread.sleep(300L);
                if (this.last_message.contains("code")) break;
            }
            if (this.last_message.startsWith("Your login code:")) {
                str = this.last_message.substring(17, 22).trim();
            }
            if (str.length() == 5 && this.last_message.contains("Your login code:")) {
                this.deleteAccount();
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException index) {
                    // empty catch block
                }
                telegramApi2.sentCode.setPhoneregfalse();
                telegramApi2.Authrize(str, first_name, last_name);
                this.logWithSenderNumber("Acconut reseted!");
                return true;
            }
            this.logWithSenderNumber("Code not received.");
            return false;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean changeCurrentSession(String password) {
        try {
            this.logWithSenderNumber("Reseting session.....");
            if (!this.setStatus(false, 7500)) {
                this.logWithSenderNumber("Cant set status.");
                return false;
            }
            this.sendMessageByUid(777000, "" + this.rnd.nextLong() + "");
            telegramApi telegramApi2 = new telegramApi(this.index);
            this.last_message = "";
            telegramApi2.login(this.SenderPhoneNo, true);
            String code = "";
            for (int index = 0; index < 5; ++index) {
                System.out.println("geting last message... attemp " + index + 1);
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (this.last_message.contains("code")) break;
            }
            if (this.last_message.startsWith("Your login code:")) {
                code = this.last_message.substring(17, 22).trim();
            }
            if (code.length() == 5) {
                telegramApi2.Authrize(code, "", "", password);
                this.logWithSenderNumber("Session reseted!");
                return true;
            }
            this.logWithSenderNumber("Code not received.");
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }

    public String get_channel_link(String username) {
        try {
            if (!this.resolveChannelUserName(username)) {
                return null;
            }
            TLInputChannel ch = new TLInputChannel();
            ch.setAccessHash(this.channel.getAccessHash());
            ch.setChannelId(this.channel.getId());
            TLRequestChannelsExportInvite tr = new TLRequestChannelsExportInvite();
            tr.setChannel(ch);
            TLChatInviteExported invite = (TLChatInviteExported)this.api.doRpcCall(tr);
            return invite.getLink();
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            return null;
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (TimeoutException ex) {
            java.util.logging.Logger.getLogger(telegramApi.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void AuthrizeToOtherDC(int to) {
        try {
            TLRequestAuthExportAuthorization raa = new TLRequestAuthExportAuthorization();
            raa.setDcId(to);
            TLExportedAuthorization exportedAuthorization = (TLExportedAuthorization)this.api.doRpcCall(raa);
            TLRequestAuthImportAuthorization tia = new TLRequestAuthImportAuthorization();
            tia.setBytes(exportedAuthorization.getBytes());
            tia.setId(to);
            this.api.doRpcCallNonAuth(tia, to);
            return;
        }
        catch (Exception raa) {
            return;
        }
    }

    public void resetNotifications() {
        try {
            this.api.doRpcCall(new TLRequestAccountResetNotifySettings(), null);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void disableLogging() {
        org.telegram.mtproto.log.Logger.registerInterface(new LogInterface(){

            @Override
            public void w(String tag, String message) {
            }

            @Override
            public void d(String tag, String message) {
            }

            @Override
            public void e(String tag, Throwable t) {
            }

            @Override
            public void e(String tag, String message) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        Logger.registerInterface(new LoggerInterface(){

            @Override
            public void w(String tag, String message) {
            }

            @Override
            public void d(String tag, String message) {
            }

            @Override
            public void e(String tag, Throwable t) {
            }
        });
    }

    public boolean loadServerConfigurations() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void getDifferences() {
        TLRequestUpdatesGetDifference requestUpdatesGetDifference = new TLRequestUpdatesGetDifference();
        requestUpdatesGetDifference.setQts(0);
        TLAbsDifference absDifference = null;
        do {
            requestUpdatesGetDifference.setDate(0);
            requestUpdatesGetDifference.setPts(0);
            requestUpdatesGetDifference.setQts(-1);
            try {
                absDifference = (TLAbsDifference)this.api.doRpcCall(requestUpdatesGetDifference);
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                telegramApi telegramApi2 = this;
                synchronized (telegramApi2) {
                    if (absDifference instanceof TLDifferenceSlice) {
                        this.wait(100L);
                    }
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        } while (absDifference instanceof TLDifferenceSlice);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void getChannelDifferencesInternal(int chatId, long accessHash) {
        TLRequestUpdatesGetChannelDifference requestGetChannelDifference = new TLRequestUpdatesGetChannelDifference();
        requestGetChannelDifference.setFilter(new TLChannelMessagesFilterEmpty());
        TLInputChannel inputChannel = new TLInputChannel();
        inputChannel.setChannelId(chatId);
        inputChannel.setAccessHash(accessHash);
        requestGetChannelDifference.setPeer(inputChannel);
        TLAbsUpdatesChannelDifferences absDifference = null;
        do {
            boolean pts = false;
            requestGetChannelDifference.setPts(1);
            requestGetChannelDifference.setLimit(100);
            try {
                absDifference = (TLAbsUpdatesChannelDifferences)this.api.doRpcCall(requestGetChannelDifference);
                if (absDifference != null && absDifference instanceof TLUpdatesChannelDifferencesEmpty) {
                    // empty if block
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                telegramApi telegramApi2 = this;
                synchronized (telegramApi2) {
                    if (absDifference instanceof TLUpdatesChannelDifferencesTooLong) {
                        this.wait(100L);
                    }
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        } while (absDifference instanceof TLUpdatesChannelDifferencesTooLong);
    }

    private boolean createApi(int apiId, MemoryApiState _loadState, boolean issimple) throws IOException {
        try {
            this.apiState = _loadState == null ? new MemoryApiState(true) : _loadState;
            this.api = new TelegramApi(this.apiState, new AppInfo(apiId, this.DeviceModel, this.systemVersion, this.appVersion, this.langCode), new ApiCallback(){

                @Override
                public void onAuthCancelled(TelegramApi api) {
                }

                @Override
                public void onUpdatesInvalidated(TelegramApi api) {
                }

                @Override
                public void onUpdate(TLAbsUpdates updates) {
                    if (updates instanceof TLUpdateShortMessage) {
                        telegramApi.this.onIncomingMessageUser(((TLUpdateShortMessage)updates).getUserId(), ((TLUpdateShortMessage)updates).getMessage());
                        TLUpdateShortMessage updateShortMessage = (TLUpdateShortMessage)updates;
                        UpdateWrapper wrapper = new UpdateWrapper(updateShortMessage);
                        wrapper.setParams(updateShortMessage.getPts(), updateShortMessage.getPtsCount(), updateShortMessage.getDate(), 0, 0);
                        telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                    } else if (updates instanceof TLUpdateShortChatMessage) {
                        TLUpdateShortChatMessage updateShortChatMessage = (TLUpdateShortChatMessage)updates;
                        UpdateWrapper wrapper = new UpdateWrapper(updateShortChatMessage);
                        wrapper.setParams(updateShortChatMessage.getPts(), updateShortChatMessage.getPtsCount(), updateShortChatMessage.getDate(), 0, 0);
                        telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                    } else if (updates instanceof TLUpdateShort) {
                        TLUpdateShort updateShort = (TLUpdateShort)updates;
                        UpdateWrapper wrapper = new UpdateWrapper(updateShort.getUpdate());
                        wrapper.setParams(updateShort.getUpdate().getPts(), updateShort.getUpdate().getPtsCount(), updateShort.getDate(), 0, 0);
                        telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                    } else if (updates instanceof TLUpdates) {
                        TLUpdates tlUpdates = (TLUpdates)updates;
                        boolean disablePtsCheck = tlUpdates.getSeq() != 0;
                        boolean correctSeq = true;
                        if (disablePtsCheck) {
                            correctSeq = telegramApi.this.updatesHandler.checkSeq(tlUpdates.getSeq(), 0, tlUpdates.getDate());
                        }
                        if (correctSeq) {
                            telegramApi.this.updatesHandler.onChats(tlUpdates.getChats());
                            telegramApi.this.updatesHandler.onUsers(tlUpdates.getUsers());
                            tlUpdates.getUpdates().forEach(x -> {
                                UpdateWrapper wrapper = new UpdateWrapper((TLObject)x);
                                wrapper.setParams(x.getPts(), x.getPtsCount(), tlUpdates.getDate(), tlUpdates.getSeq(), 0);
                                if (disablePtsCheck) {
                                    wrapper.disablePtsCheck();
                                }
                                telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                            });
                        } else {
                            telegramApi.this.updatesHandler.getDifferences();
                        }
                    } else if (updates instanceof TLUpdatesCombined) {
                        TLUpdatesCombined updatesCombined = (TLUpdatesCombined)updates;
                        boolean disablePtsCheck = updatesCombined.getSeq() != 0;
                        boolean correctSeq = true;
                        if (disablePtsCheck) {
                            correctSeq = telegramApi.this.updatesHandler.checkSeq(updatesCombined.getSeq(), updatesCombined.getSeqStart(), updatesCombined.getDate());
                        }
                        if (correctSeq) {
                            telegramApi.this.updatesHandler.onChats(updatesCombined.getChats());
                            telegramApi.this.updatesHandler.onUsers(updatesCombined.getUsers());
                            updatesCombined.getUpdates().forEach(x -> {
                                UpdateWrapper wrapper = new UpdateWrapper((TLObject)x);
                                wrapper.setParams(x.getPts(), x.getPtsCount(), updatesCombined.getDate(), updatesCombined.getSeq(), updatesCombined.getSeqStart());
                                if (disablePtsCheck) {
                                    wrapper.disablePtsCheck();
                                }
                                telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                            });
                        } else {
                            telegramApi.this.updatesHandler.getDifferences();
                        }
                    } else if (updates instanceof TLUpdateShortSentMessage) {
                        TLUpdateShortSentMessage updateShortSentMessage = (TLUpdateShortSentMessage)updates;
                        UpdateWrapper wrapper = new UpdateWrapper(updateShortSentMessage);
                        wrapper.setParams(updateShortSentMessage.getPts(), updateShortSentMessage.getPtsCount(), updateShortSentMessage.getDate(), 0, 0);
                        telegramApi.this.updateHandlerThread.addUpdate(wrapper);
                    } else if (updates instanceof TLUpdatesTooLong) {
                        telegramApi.this.updatesHandler.onTLUpdatesTooLong();
                    } else {
                        telegramApi.this.logWithSenderNumber("Unsupported TLAbsUpdates: " + updates.toString());
                    }
                }
            });
            this.updatesHandler = new UpdatesHandlerBase(){

                @Override
                protected void onTLUpdateChatParticipantsCustom(TLUpdateChatParticipants update) {
                    TLInputPeerChat ch = new TLInputPeerChat();
                    ch.setChatId(update.getParticipants().getChatId());
                    if (telegramApi.this.hideReportSpam(ch)) {
                        telegramApi.this.logWithSenderNumber("Report spam was hidden for chat" + update.getParticipants().getChatId());
                    }
                }

                @Override
                protected void onTLUpdateNewMessageCustom(TLUpdateNewMessage update) {
                }

                @Override
                protected void onTLUpdateChannelNewMessageCustom(TLUpdateChannelNewMessage update) {
                }

                @Override
                protected void onTLUpdateChannelCustom(TLUpdateChannel update) {
                }

                @Override
                protected void onTLUpdateBotInlineQueryCustom(TLUpdateBotInlineQuery update) {
                }

                @Override
                protected void onTLUpdateBotInlineSendCustom(TLUpdateBotInlineSend update) {
                }

                @Override
                protected void onTLUpdateChannelGroupCustom(TLUpdateChannelGroup update) {
                }

                @Override
                protected void onTLUpdateChannelMessageViewsCustom(TLUpdateChannelMessageViews update) {
                }

                @Override
                protected void onTLUpdateChannelPinnedMessageCustom(TLUpdateChannelPinnedMessage update) {
                }

                @Override
                protected void onTLUpdateChatAdminCustom(TLUpdateChatAdmin update) {
                }

                @Override
                protected void onTLUpdateChatParticipantAddCustom(TLUpdateChatParticipantAdd update) {
                }

                @Override
                protected void onTLUpdateChatParticipantAdminCustom(TLUpdateChatParticipantAdmin update) {
                }

                @Override
                protected void onTLUpdateChatParticipantDeleteCustom(TLUpdateChatParticipantDelete update) {
                }

                @Override
                protected void onTLUpdateChatUserTypingCustom(TLUpdateChatUserTyping update) {
                }

                @Override
                protected void onTLUpdateContactLinkCustom(TLUpdateContactLink update) {
                }

                @Override
                protected void onTLUpdateContactRegisteredCustom(TLUpdateContactRegistered update) {
                }

                @Override
                protected void onTLUpdateDcOptionsCustom(TLUpdateDcOptions update) {
                }

                @Override
                protected void onTLUpdateDeleteChannelMessagesCustom(TLUpdateDeleteChannelMessages update) {
                }

                @Override
                protected void onTLUpdateDeleteMessagesCustom(TLUpdateDeleteMessages update) {
                }

                @Override
                protected void onTLUpdateEditChannelMessageCustom(TLUpdateEditChannelMessage update) {
                }

                @Override
                protected void onTLUpdateMessageIdCustom(TLUpdateMessageId update) {
                }

                @Override
                protected void onTLUpdateNewAuthorizationCustom(TLUpdateNewAuthorization update) {
                }

                @Override
                protected void onTLUpdateNewStickerSetCustom(TLUpdateNewStickerSet update) {
                }

                @Override
                protected void onTLUpdateNotifySettingsCustom(TLUpdateNotifySettings update) {
                }

                @Override
                protected void onTLUpdatePrivacyCustom(TLUpdatePrivacy update) {
                }

                @Override
                protected void onTLUpdateReadChannelInboxCustom(TLUpdateReadChannelInbox update) {
                }

                @Override
                protected void onTLUpdateReadMessagesContentsCustom(TLUpdateReadMessagesContents update) {
                }

                @Override
                protected void onTLUpdateReadMessagesInboxCustom(TLUpdateReadMessagesInbox update) {
                }

                @Override
                protected void onTLUpdateReadMessagesOutboxCustom(TLUpdateReadMessagesOutbox update) {
                }

                @Override
                protected void onTLUpdateSavedGifsCustom(TLUpdateSavedGifs update) {
                }

                @Override
                protected void onTLUpdateServiceNotificationCustom(TLUpdateServiceNotification update) {
                }

                @Override
                protected void onTLUpdateStickerSetsCustom(TLUpdateStickerSets update) {
                }

                @Override
                protected void onTLUpdateStickerSetsOrderCustom(TLUpdateStickerSetsOrder update) {
                }

                @Override
                protected void onTLUpdateUserBlockedCustom(TLUpdateUserBlocked update) {
                }

                @Override
                protected void onTLUpdateUserNameCustom(TLUpdateUserName update) {
                }

                @Override
                protected void onTLUpdateUserPhoneCustom(TLUpdateUserPhone update) {
                }

                @Override
                protected void onTLUpdateUserPhotoCustom(TLUpdateUserPhoto update) {
                }

                @Override
                protected void onTLUpdateUserStatusCustom(TLUpdateUserStatus update) {
                }

                @Override
                protected void onTLUpdateUserTypingCustom(TLUpdateUserTyping update) {
                }

                @Override
                protected void onTLUpdateWebPageCustom(TLUpdateWebPage update) {
                }

                @Override
                protected void onTLFakeUpdateCustom(TLFakeUpdate update) {
                }

                @Override
                protected void onTLUpdateShortMessageCustom(TLUpdateShortMessage update) {
                    System.out.println("cus");
                }

                @Override
                protected void onTLUpdateShortChatMessageCustom(TLUpdateShortChatMessage update) {
                }

                @Override
                protected void onTLUpdateShortSentMessageCustom(TLUpdateShortSentMessage update) {
                }

                @Override
                protected void onTLUpdateBotCallbackQueryCustom(TLUpdateBotCallbackQuery update) {
                }

                @Override
                protected void onTLUpdateEditMessageCustom(TLUpdateEditMessage update) {
                }

                @Override
                protected void onTLUpdateInlineBotCallbackQueryCustom(TLUpdateInlineBotCallbackQuery update) {
                }

                @Override
                protected void onTLAbsMessageCustom(TLAbsMessage message) {
                }

                @Override
                protected void onUsersCustom(List<TLAbsUser> users) {
                }

                @Override
                protected void onChatsCustom(List<TLAbsChat> chats) {
                }
            };
            this.updateHandlerThread = new UpdateHandlerThread();
            this.updateHandlerThread.start();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean hideReportSpam(TLAbsInputPeer peer) {
        try {
            TLRequestMessagesHideReportSpam hid = new TLRequestMessagesHideReportSpam();
            hid.setPeer(peer);
            this.api.doRpcCallNonAuth(hid);
            return true;
        }
        catch (RpcException ex) {
            this.logWithSenderNumber("Err." + ex.getErrorTag());
            return false;
        }
        catch (Exception rt) {
            return false;
        }
    }

    public void setchatAdmin(TLUser ua) {
        try {
            TLRequestMessagesToggleChatAdmins ta = new TLRequestMessagesToggleChatAdmins();
            ta.setEnabled(true);
            ta.setChatId(this.chatId);
            TLUpdates tLUpdates = (TLUpdates)this.api.doRpcCall(ta);
        }
        catch (RpcException ex2) {
            this.logWithSenderNumber("Err." + ex2.getErrorTag());
        }
        catch (Exception ex2) {
            // empty catch block
        }
        try {
            TLRequestMessagesEditChatAdmin ca = new TLRequestMessagesEditChatAdmin();
            ca.setAdmin(true);
            ca.setChatId(this.chatId);
            TLInputPeerUser us = new TLInputPeerUser();
            us.setUserId(ua.getId());
            us.setAccessHash(ua.getAccessHash());
            ca.setUserId(us);
            if (this.api.doRpcCall(ca) instanceof TLBoolTrue) {
                this.logWithSenderNumber("changed admin" + this.bot.getId());
            }
        }
        catch (RpcException ex3) {
            this.logWithSenderNumber("Err." + ex3.getErrorTag());
        }
        catch (Exception ex3) {
            // empty catch block
        }
    }

    public String joinChannellink(String link) {
        try {
            this.logWithSenderNumber("joining channel...");
            TLChannel chh = null;
            String[] split = link.split("/");
            if (link.toLowerCase().contains("joinchat")) {
                try {
                    TLRequestMessagesCheckChatInvite tlch = new TLRequestMessagesCheckChatInvite();
                    tlch.setHash(split[split.length - 1]);
                    TLChatInviteAlready y = (TLChatInviteAlready)this.api.doRpcCall(tlch);
                    chh = (TLChannel)y.getChat();
                }
                catch (Exception e) {
                    TLRequestMessagesImportChatInvite ch = new TLRequestMessagesImportChatInvite();
                    ch.setHash(split[split.length - 1]);
                    TLUpdates result = (TLUpdates)this.api.doRpcCall(ch);
                    chh = (TLChannel)result.getChats().get(0);
                }
            } else {
                TLRequestContactsResolveUsername rsv = new TLRequestContactsResolveUsername();
                rsv.setUsername(split[split.length - 1]);
                TLResolvedPeer result = (TLResolvedPeer)this.api.doRpcCall(rsv);
                chh = (TLChannel)result.getChats().get(0);
                TLInputChannel ch = new TLInputChannel();
                ch.setChannelId(chh.getId());
                ch.setAccessHash(chh.getAccessHash());
                TLRequestChannelsJoinChannel chj = new TLRequestChannelsJoinChannel();
                chj.setChannel(ch);
                TLUpdates tLUpdates = (TLUpdates)this.api.doRpcCall(chj);
            }
            this.channel = chh;
        }
        catch (Exception ex) {
            this.logWithSenderNumber("probelm on joining.");
            System.out.print(ex.getMessage());
            return "";
        }
        this.logWithSenderNumber("joined.");
        return "joined!";
    }

    public String ViewChannel(String link, int limit, boolean publicjoin) {
        try {
            TLObject ch;
            TLChannel chh = null;
            String[] split = link.split("/");
            if (link.toLowerCase().contains("joinchat")) {
                try {
                    TLRequestMessagesCheckChatInvite tlch = new TLRequestMessagesCheckChatInvite();
                    tlch.setHash(split[split.length - 1]);
                    TLChatInviteAlready y = (TLChatInviteAlready)this.api.doRpcCall(tlch);
                    chh = (TLChannel)y.getChat();
                }
                catch (Exception e) {
                    ch = new TLRequestMessagesImportChatInvite();
                    ch.setHash(split[split.length - 1]);
                    TLUpdates result = (TLUpdates)this.api.doRpcCall(ch);
                    if (result.getChats().size() > 0) {
                        chh = (TLChannel)result.getChats().get(0);
                    }
                    return "Err. link";
                }
            } else {
                TLRequestContactsResolveUsername rsv = new TLRequestContactsResolveUsername();
                rsv.setUsername(split[split.length - 1]);
                TLResolvedPeer result = (TLResolvedPeer)this.api.doRpcCall(rsv);
                if (result.getChats().size() <= 0) {
                    return "Err. link";
                }
                chh = (TLChannel)result.getChats().get(0);
                TLInputChannel ch2 = new TLInputChannel();
                ch2.setChannelId(chh.getId());
                ch2.setAccessHash(chh.getAccessHash());
                if (publicjoin) {
                    TLRequestChannelsJoinChannel chj = new TLRequestChannelsJoinChannel();
                    chj.setChannel(ch2);
                    TLUpdates tLUpdates = (TLUpdates)this.api.doRpcCall(chj);
                }
            }
            if (chh == null) {
                return "";
            }
            this.channel = chh;
            TLRequestChannelsGetImportantHistory _history = new TLRequestChannelsGetImportantHistory();
            ch = new TLInputChannel();
            ch.setChannelId(chh.getId());
            ch.setAccessHash(chh.getAccessHash());
            _history.setChannel((TLAbsInputChannel)ch);
            _history.setAddOffset(0);
            _history.setLimit(limit);
            _history.setMaxId(0);
            _history.setMinId(0);
            _history.setOffsetDate(0);
            _history.setOffsetId(0);
            TLAbsMessages dialogs = (TLAbsMessages)this.api.doRpcCall(_history);
            TLIntVector tiv = new TLIntVector();
            for (TLAbsMessage message : dialogs.getMessages()) {
                if (!(message instanceof TLMessage)) continue;
                tiv.add(((TLMessage)message).getId());
                System.out.println("Channel " + chh.getTitle() + " msg :" + ((TLMessage)message).getMessage() + " with id " + chh.getId() + "msg view: " + ((TLMessage)message).getViews());
            }
            try {
                TLRequestChannelsReadHistory read_his = new TLRequestChannelsReadHistory();
                TLInputChannel rh = new TLInputChannel();
                rh.setAccessHash(chh.getAccessHash());
                rh.setChannelId(chh.getId());
                read_his.setChannel(rh);
                read_his.setMaxId(0);
                this.api.doRpcCall(read_his);
                TLRequestMessagesGetMessagesViews inc_view = new TLRequestMessagesGetMessagesViews();
                TLInputPeerChannel ch_peer = new TLInputPeerChannel();
                ch_peer.setAccessHash(chh.getAccessHash());
                ch_peer.setChannelId(chh.getId());
                inc_view.setId(tiv);
                inc_view.setIncrement(true);
                inc_view.setPeer(ch_peer);
                this.api.doRpcCall(inc_view, null);
            }
            catch (Exception iox) {
                System.out.print(iox.toString());
                return null;
            }
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            return null;
        }
        return "";
    }

    public void UpdateProfile(String name, String family, String src) {
        File file;
        if (!name.isEmpty() || !family.isEmpty()) {
            block8 : {
                System.out.println("Updating account profile...");
                String FirstName = name;
                String LastName = family;
                try {
                    TLRequestAccountUpdateProfile acu = new TLRequestAccountUpdateProfile();
                    acu.setAbout("telep");
                    acu.setFirstName(FirstName);
                    acu.setLastName(LastName);
                    TLUser tLUser = (TLUser)this.api.doRpcCall(acu);
                }
                catch (Exception e) {
                    if (!e.getMessage().contains("NAME_NOT_MODIFIED")) break block8;
                    System.out.println("Profile is Uptodate.");
                }
            }
            System.out.println("Account profile updated.");
        }
        if ((file = new File(src)).exists()) {
            try {
                System.out.println("Updating account avatar...");
                String fileName = src;
                int task = this.api.getUploader().requestTask(fileName, null);
                this.api.getUploader().waitForTask(task);
                int resultState = this.api.getUploader().getTaskState(task);
                Uploader.UploadResult result = this.api.getUploader().getUploadResult(task);
                if (result.isUsedBigFile()) {
                    TLInputFileBig inputFile = new TLInputFileBig();
                    inputFile.setId(result.getFileId());
                    inputFile.setName(fileName);
                    inputFile.setParts(result.getPartsCount());
                } else {
                    TLAbsInputFile inputFile = new TLInputFile();
                    inputFile = new TLInputFileBig();
                    inputFile.setId(result.getFileId());
                    inputFile.setName(fileName);
                    inputFile.setParts(result.getPartsCount());
                }
                TLRequestPhotosUploadProfilePhoto tph = new TLRequestPhotosUploadProfilePhoto();
                TLInputFile pro = new TLInputFile();
                pro.setId(result.getFileId());
                pro.setName("profile.jpg");
                pro.setParts(result.getPartsCount());
                pro.setMd5Checksum(result.getHash());
                tph.setFile(pro);
                tph.setCaption("MyPhoto");
                tph.setCrop(new TLInputPhotoCropAuto());
                tph.setGeoPoint(new TLInputGeoPointEmpty());
                TLPhoto y = (TLPhoto)this.api.doRpcCall(tph);
                System.out.println("Account avatar updated.");
            }
            catch (Exception fileName) {
                // empty catch block
            }
        }
        System.out.println("Profile Updated.");
    }

    public String login(String Phone, boolean resendCode) throws IOException, Exception {
        return this.login(Phone, resendCode, false, false);
    }

    public String login(String Phone) throws Exception {
        return this.login(Phone, false, false, false);
    }

    private String login(String phone, int apiId, String apiHash, boolean sendcall) throws IOException, Exception {
        block14 : {
            System.out.println("Loging in for " + this.SenderPhoneNo + "...");
            System.out.print("Loading fresh DC list...");
            try {
                TLConfig config = (TLConfig)this.api.doRpcCallNonAuth(new TLRequestHelpGetConfig());
                this.apiState.updateSettings(config);
            }
            catch (Exception e) {
                return "Err." + e.getMessage();
            }
            System.out.println("ok");
            System.out.println("Sending sms to phone " + phone + "...");
            int destDC = 0;
            try {
                TLRequestAuthSendCode asc = new TLRequestAuthSendCode();
                try {
                    asc.setPhoneNumber(phone);
                    asc.setApiId(apiId);
                    asc.setApiHash(apiHash);
                    asc.setLangCode("en");
                    if (sendcall) {
                        asc.setallowflashcall(true);
                    } else {
                        asc.setallowflashcall(false);
                    }
                    this.sentCode = (TLSentCode)this.api.doRpcCallNonAuth(asc);
                }
                catch (RpcException e) {
                    if (e.getErrorCode() == 303) {
                        if (e.getErrorTag().startsWith("NETWORK_MIGRATE_")) {
                            destDC = Integer.parseInt(e.getErrorTag().substring("NETWORK_MIGRATE_".length()));
                        } else if (e.getErrorTag().startsWith("PHONE_MIGRATE_")) {
                            destDC = Integer.parseInt(e.getErrorTag().substring("PHONE_MIGRATE_".length()));
                        } else if (e.getErrorTag().startsWith("USER_MIGRATE_")) {
                            destDC = Integer.parseInt(e.getErrorTag().substring("USER_MIGRATE_".length()));
                        }
                        this.dcId = destDC;
                        this.api.switchToDc(destDC);
                        this.sentCode = (TLSentCode)this.api.doRpcCallNonAuth(asc);
                        break block14;
                    }
                    return e.getErrorTag();
                }
            }
            catch (Exception tx) {
                return "Err." + tx.getMessage();
            }
        }
        return "Code Sent";
    }

    /*
     * Exception decompiling
     */
    public String login(String Phone, boolean resendCode, boolean sendCall, boolean isSimple) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:416)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:468)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2960)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:818)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:372)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:141)
        // org.benf.cfr.reader.Main.main(Main.java:242)
        throw new IllegalStateException("Decompilation failed");
    }

    public static int getRandomNumberFrom(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt(max + 1 - min) + min;
        return randomNumber;
    }

    public TLImportedContacts importContacts(String[] con) throws Exception {
        try {
            TLVector<TLInputPhoneContact> contacts = new TLVector<TLInputPhoneContact>();
            for (int i = 0; i < con.length; ++i) {
                String PhoneNo = con[i];
                if (!PhoneNo.startsWith("+")) {
                    PhoneNo = "+" + PhoneNo;
                }
                TLInputPhoneContact contact = new TLInputPhoneContact();
                contact.setClientId(1L);
                contact.setFirstName(PhoneNo);
                contact.setLastName("c");
                contact.setPhone(PhoneNo);
                contacts.add(contact);
            }
            TLRequestContactsImportContacts importContacts = new TLRequestContactsImportContacts();
            importContacts.setContacts(contacts);
            importContacts.setReplace(false);
            return (TLImportedContacts)this.api.doRpcCall(importContacts);
        }
        catch (RpcException t) {
            this.logWithSenderNumber(t.getErrorTag());
            return null;
        }
    }

    public void ChangePhone() throws IOException, TimeoutException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\r\nphone want change to:");
        String phone = reader.readLine();
        System.out.print("\r\nSending sms to phone " + phone + "...");
        TLSentCode sentChangeCode = null;
        try {
            TLRequestAccountSendChangePhoneCode asc = new TLRequestAccountSendChangePhoneCode();
            asc.setPhoneNumber(phone);
            sentChangeCode = (TLSentCode)this.api.doRpcCall(asc);
        }
        catch (RpcException e) {
            if (e.getErrorCode() != 303 || e.getErrorTag().contains("PHONE_NUMBER_INVALID") || e.getErrorTag().contains("PHONE_CODE_EMPTY") || e.getErrorTag().contains("PHONE_CODE_INVALID") || e.getErrorTag().contains("PHONE_CODE_EXPIRED") || e.getErrorTag().startsWith("FLOOD_WAIT") || e.getErrorTag().startsWith("PHONE_NUMBER_OCCUPIED")) {
                // empty if block
            }
            System.out.print(e.getErrorTag());
        }
        System.out.print("Sent.");
        System.out.print("code:");
        String code = reader.readLine();
        if (code == "" || code.length() < 3) {
            return;
        }
        try {
            TLRequestAccountChangePhone ach = new TLRequestAccountChangePhone();
            ach.setPhoneNumber(phone);
            ach.setPhoneCode(code);
            ach.setPhoneCodeHash(sentChangeCode.getPhoneCodeHash());
            TLAbsUser tLAbsUser = (TLAbsUser)this.api.doRpcCall(ach);
        }
        catch (RpcException e) {
            if (e.getErrorCode() != 303 || e.getErrorTag().contains("PHONE_NUMBER_INVALID") || e.getErrorTag().contains("PHONE_CODE_EMPTY") || e.getErrorTag().contains("PHONE_CODE_INVALID") || e.getErrorTag().contains("PHONE_CODE_EXPIRED") || e.getErrorTag().startsWith("FLOOD_WAIT") || e.getErrorTag().startsWith("PHONE_NUMBER_OCCUPIED")) {
                // empty if block
            }
            System.out.print(e.getErrorTag());
        }
        System.out.println("Change complete.");
        System.out.print("Loading initial state...");
        try {
            this.api.doRpcCall(new TLRequestUpdatesGetState());
        }
        catch (Exception e) {
            System.out.println("err.");
        }
        System.out.println("loaded.");
    }

    private String login() throws IOException {
        System.out.print("Loging in for " + this.SenderPhoneNo + "...");
        System.out.println("Activation complete.");
        System.out.print("Loading initial state...");
        try {
            this.api.doRpcCall(new TLRequestUpdatesGetState(), 8000);
        }
        catch (RpcException e) {
            this.logWithSenderNumber("err." + e.getErrorTag());
            this.Mov_block(this.SenderPhoneNo);
            return "err." + e.getErrorTag();
        }
        catch (TimeoutException ex) {
            this.Mov_block(this.SenderPhoneNo);
            this.logWithSenderNumber("err." + ex.getMessage());
            return "err." + ex.getMessage();
        }
        System.out.println("loaded.");
        return "loaded.";
    }

    public boolean Convert2SuperGroup() {
        try {
            TLRequestMessagesMigrateChat rmm = new TLRequestMessagesMigrateChat();
            rmm.setChatId(this.chatId);
            TLUpdates up = (TLUpdates)this.api.doRpcCall(rmm);
            TLChannel ch = (TLChannel)up.getChats().get(0);
            this.chatId = ch.getId();
            this.chathash = ch.getAccessHash();
            return true;
        }
        catch (RpcException e) {
            this.logWithSenderNumber("Err." + e.getErrorTag());
            return false;
        }
        catch (IOException ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return false;
        }
        catch (TimeoutException ex) {
            this.logWithSenderNumber("Err." + ex.getMessage());
            return false;
        }
    }

    public String sendMessageToPhone(String PhoneNo, String Message) throws IOException {
        TLInputPeerUser inputPeerContact = new TLInputPeerUser();
        try {
            inputPeerContact = this.addContact(PhoneNo);
            if (this.AuthInvalidated) {
                return "Auth_Invalidated";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage() == "not in telegram.") {
                return e.getMessage();
            }
            if (e.getMessage().contains("FLOOD_WAIT")) {
                System.out.println("\tFLOOD_WAIT.");
                return "FLOOD_WAIT";
            }
            return e.getMessage();
        }
        try {
            TLRequestMessagesSendMessage sendMessageRequest = new TLRequestMessagesSendMessage();
            sendMessageRequest.setPeer(inputPeerContact);
            sendMessageRequest.setMessage(Message);
            sendMessageRequest.setRandomId(this.rnd.nextLong());
            this.api.doRpcCall(sendMessageRequest);
            return PhoneNo + ": Sent";
        }
        catch (Exception e) {
            if (e.getMessage().contains("FLOOD_WAIT")) {
                System.out.println("\tFLOOD_WAIT.");
                return "FLOOD_WAIT";
            }
            return "Sent.";
        }
    }

    public void logWithSenderNumber(String message) {
        MyLogger.log(0, this.SenderPhoneNo + "#" + message);
        System.out.println(this.SenderPhoneNo + "#" + message);
    }

    public static TLConfig refreshServerConfiguration(TelegramApi myApi) {
        try {
            MyLogger.log(0, "Getting fresh dc list...");
            serverConfiguration = (TLConfig)myApi.doRpcCallNonAuth(new TLRequestHelpGetConfig());
            for (TLDcOption tlDcOption : serverConfiguration.getDcOptions()) {
                MyLogger.log(0, "" + tlDcOption.getId() + "-" + tlDcOption.getIpAddress() + "-" + tlDcOption.getPort());
            }
            FileOutputStream fileOutputStream = new FileOutputStream(JarPath.GetPath() + "\\activeServers.conf");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serverConfiguration);
            objectOutputStream.close();
            fileOutputStream.close();
            myApi.getState().updateSettings(serverConfiguration);
            MyLogger.log(0, "Renewed.");
            return serverConfiguration;
        }
        catch (Exception ex) {
            MyLogger.log(0, "Refresh Configuration: " + ex.getMessage());
            return null;
        }
    }

    public static TLConfig loadServerConfigurations(TelegramApi myApi) {
        try {
            if (!new File(JarPath.GetPath() + "\\activeServers.conf").exists()) {
                return telegramApi.refreshServerConfiguration(myApi);
            }
            FileInputStream fileInputStream = new FileInputStream(JarPath.GetPath() + "\\activeServers.conf");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            serverConfiguration = (TLConfig)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            myApi.getState().updateSettings(serverConfiguration);
            MyLogger.log(0, "Servers Configuration Loaded.");
            return serverConfiguration;
        }
        catch (Exception ex) {
            MyLogger.log(0, "Load Server Configuration: " + ex.getMessage());
            return null;
        }
    }

    public String checkPhone(String PhoneNo) throws IOException, RpcException, TimeoutException {
        boolean registered;
        block9 : {
            this.createApi(apiId, null, false);
            TLConfig config = telegramApi.loadServerConfigurations(this.api);
            this.apiState.updateSettings(config);
            this.logWithSenderNumber("Checking phone " + PhoneNo + " ...");
            TLRequestAuthCheckPhone checkPhone = new TLRequestAuthCheckPhone();
            checkPhone.setPhoneNumber(PhoneNo);
            TLCheckedPhone checkedPhone = null;
            boolean invited = false;
            registered = false;
            try {
                checkedPhone = (TLCheckedPhone)this.api.doRpcCallNonAuth(checkPhone);
                registered = checkedPhone.isPhoneRegistered();
            }
            catch (RpcException e) {
                int destDC;
                if (e.getErrorCode() != 303) break block9;
                if (e.getErrorTag().startsWith("NETWORK_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("NETWORK_MIGRATE_".length()));
                } else if (e.getErrorTag().startsWith("PHONE_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("PHONE_MIGRATE_".length()));
                } else if (e.getErrorTag().startsWith("USER_MIGRATE_")) {
                    destDC = Integer.parseInt(e.getErrorTag().substring("USER_MIGRATE_".length()));
                } else {
                    throw e;
                }
                this.api.switchToDc(destDC);
                checkedPhone = (TLCheckedPhone)this.api.doRpcCallNonAuth(checkPhone);
                registered = checkedPhone.isPhoneRegistered();
            }
        }
        if (registered) {
            this.logWithSenderNumber("\tis Member.");
            return "IsMember";
        }
        this.logWithSenderNumber("\tis not Member.");
        return "NotMember.";
    }

    private TLInputPeerUser addContact(String PhoneNo) throws Exception {
        TLInputPhoneContact contact = new TLInputPhoneContact();
        contact.setClientId(1L);
        contact.setFirstName(PhoneNo);
        contact.setLastName("c");
        contact.setPhone(PhoneNo);
        TLVector<TLInputPhoneContact> contacts = new TLVector<TLInputPhoneContact>();
        contacts.add(contact);
        TLRequestContactsImportContacts importContacts = new TLRequestContactsImportContacts();
        importContacts.setContacts(contacts);
        importContacts.setReplace(true);
        TLImportedContacts importedContacts = (TLImportedContacts)this.api.doRpcCall(importContacts);
        TLAbsUser recipient = null;
        try {
            recipient = importedContacts.getUsers().get(0);
        }
        catch (Exception e) {
            throw new Exception("not in telegram.");
        }
        TLInputPeerUser tu = new TLInputPeerUser();
        tu.setAccessHash(recipient.hashCode());
        tu.setUserId(recipient.getId());
        return tu;
    }

    private String readFile(String path) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String line = "";
        String file = "";
        while ((line = in.readLine()) != null) {
            file = file + line + "\n";
        }
        in.close();
        return file;
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    private void saveState(String phoneNo, MemoryApiState m) {
        try {
            FileOutputStream fileOut = new FileOutputStream(JarPath.GetPath() + "\\accounts\\" + phoneNo + ".stat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(m);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    private boolean Mov_block(String phoneNo) {
        try {
            File file = new File(JarPath.GetPath() + "\\blacked");
            if (!file.exists() && !file.mkdir()) {
                return false;
            }
            try {
                File afile = new File(JarPath.GetPath() + "\\accounts\\" + phoneNo + ".stat");
                if (afile.renameTo(new File(JarPath.GetPath() + "\\blacked\\" + phoneNo + ".stat"))) {
                    System.out.println("number is blacked successful!");
                } else {
                    System.out.println("number is failed to move blacked!");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        catch (Exception i) {
            System.out.print(i.toString());
            return false;
        }
    }

    private MemoryApiState loadState(String phoneNo) {
        try {
            FileInputStream fileIn = new FileInputStream(JarPath.GetPath() + "\\accounts\\" + phoneNo + ".stat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            MemoryApiState m = (MemoryApiState)in.readObject();
            in.close();
            fileIn.close();
            return m;
        }
        catch (IOException i) {
            System.out.print(i.toString());
            return null;
        }
        catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }

    private class UpdateHandlerThread
    extends Thread {
        private boolean isAlive = true;
        private final PriorityQueue<UpdateWrapper> updates = new PriorityQueue(new UpdateWrapper.UpdateWrapperComparator());

        private UpdateHandlerThread() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void addUpdate(UpdateWrapper newUpdate) {
            PriorityQueue<UpdateWrapper> priorityQueue = this.updates;
            synchronized (priorityQueue) {
                this.updates.offer(newUpdate);
                this.updates.notifyAll();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void addUpdates(List<UpdateWrapper> newUpdates) {
            PriorityQueue<UpdateWrapper> priorityQueue = this.updates;
            synchronized (priorityQueue) {
                this.updates.addAll(newUpdates);
                this.updates.notifyAll();
            }
        }

        @Override
        public void interrupt() {
            this.isAlive = false;
            super.interrupt();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (this.isAlive) {
                while (this.isAlive) {
                    try {
                        UpdateWrapper update;
                        PriorityQueue<UpdateWrapper> priorityQueue = this.updates;
                        synchronized (priorityQueue) {
                            update = this.updates.poll();
                        }
                        if (update == null) {
                            priorityQueue = this.updates;
                            synchronized (priorityQueue) {
                                try {
                                    this.updates.wait();
                                }
                                catch (InterruptedException e) {
                                    telegramApi.this.logWithSenderNumber("Err." + e);
                                }
                                continue;
                            }
                        }
                        telegramApi.this.updatesHandler.processUpdate(update);
                    }
                    catch (Exception e) {
                        telegramApi.this.logWithSenderNumber("Err." + e);
                    }
                }
            }
        }
    }

}

