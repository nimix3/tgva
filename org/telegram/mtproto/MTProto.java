
package org.telegram.mtproto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.mtproto.CallWrapper;
import org.telegram.mtproto.MTProtoCallback;
import org.telegram.mtproto.backoff.ExponentalBackoff;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.schedule.PrepareSchedule;
import org.telegram.mtproto.schedule.PreparedPackage;
import org.telegram.mtproto.schedule.Scheduller;
import org.telegram.mtproto.secure.CryptoUtils;
import org.telegram.mtproto.secure.Entropy;
import org.telegram.mtproto.state.AbsMTProtoState;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.state.KnownSalt;
import org.telegram.mtproto.time.TimeOverlord;
import org.telegram.mtproto.tl.MTBadMessage;
import org.telegram.mtproto.tl.MTFutureSalt;
import org.telegram.mtproto.tl.MTFutureSalts;
import org.telegram.mtproto.tl.MTGetFutureSalts;
import org.telegram.mtproto.tl.MTMessage;
import org.telegram.mtproto.tl.MTMessageCopy;
import org.telegram.mtproto.tl.MTMessageDetailedInfo;
import org.telegram.mtproto.tl.MTMessagesContainer;
import org.telegram.mtproto.tl.MTMsgsAck;
import org.telegram.mtproto.tl.MTNeedResendMessage;
import org.telegram.mtproto.tl.MTNewMessageDetailedInfo;
import org.telegram.mtproto.tl.MTNewSessionCreated;
import org.telegram.mtproto.tl.MTPing;
import org.telegram.mtproto.tl.MTPingDelayDisconnect;
import org.telegram.mtproto.tl.MTPong;
import org.telegram.mtproto.tl.MTProtoContext;
import org.telegram.mtproto.tl.MTRpcError;
import org.telegram.mtproto.tl.MTRpcResult;
import org.telegram.mtproto.transport.ConnectionType;
import org.telegram.mtproto.transport.TcpContext;
import org.telegram.mtproto.transport.TcpContextCallback;
import org.telegram.mtproto.transport.TransportRate;
import org.telegram.mtproto.util.BytesCache;
import org.telegram.mtproto.util.TimeUtil;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public class MTProto {
    private static final AtomicInteger instanceIndex = new AtomicInteger(1000);
    private static final int MESSAGES_CACHE = 3000;
    private static final int MESSAGES_CACHE_MIN = 20;
    private static final int MAX_INTERNAL_FLOOD_WAIT = 10;
    private static final int PING_INTERVAL_REQUEST = 60000;
    private static final int PING_INTERVAL = 75;
    private static final int ERROR_MSG_ID_TOO_SMALL = 16;
    private static final int ERROR_MSG_ID_TOO_BIG = 17;
    private static final int ERROR_MSG_ID_BITS = 18;
    private static final int ERROR_CONTAINER_MSG_ID_INCORRECT = 19;
    private static final int ERROR_TOO_OLD = 20;
    private static final int ERROR_SEQ_NO_TOO_SMALL = 32;
    private static final int ERROR_SEQ_NO_TOO_BIG = 33;
    private static final int ERROR_SEQ_EXPECTED_EVEN = 34;
    private static final int ERROR_SEQ_EXPECTED_ODD = 35;
    private static final int ERROR_BAD_SERVER_SALT = 48;
    private static final int ERROR_BAD_CONTAINER = 64;
    private static final int PING_TIMEOUT = 60000;
    private static final int RESEND_TIMEOUT = 60000;
    private static final int FUTURE_REQUEST_COUNT = 64;
    private static final int FUTURE_MINIMAL = 5;
    private static final long FUTURE_TIMEOUT = 1800000L;
    private final String TAG = "MTProto#" + this.INSTANCE_INDEX;
    private final int INSTANCE_INDEX = instanceIndex.incrementAndGet();
    private final HashSet<TcpContext> contexts = new HashSet();
    private final HashMap<Integer, Integer> contextConnectionId = new HashMap();
    private final HashSet<Integer> connectedContexts = new HashSet();
    private final HashSet<Integer> initedContext = new HashSet();
    private final Scheduller scheduller;
    private final ConcurrentLinkedQueue<MTMessage> inQueue = new ConcurrentLinkedQueue();
    private final ArrayList<Long> receivedMessages = new ArrayList();
    private MTProtoContext protoContext;
    private int desiredConnectionCount;
    private TcpContextCallback tcpListener;
    private ConnectionFixerThread connectionFixerThread;
    private SchedullerThread schedullerThread;
    private ResponseProcessor responseProcessor;
    private byte[] authKey;
    private byte[] authKeyId;
    private byte[] session;
    private boolean isClosed;
    private MTProtoCallback callback;
    private AbsMTProtoState state;
    private long futureSaltsRequestedTime = Long.MIN_VALUE;
    private long futureSaltsRequestId = -1L;
    private int roundRobin;
    private TransportRate connectionRate;
    private long lastPingTime = System.nanoTime() / 1000000L - 600000L;
    private ExponentalBackoff exponentalBackoff = new ExponentalBackoff(this.TAG + "#BackOff");
    private ConcurrentLinkedQueue<Long> newSessionsIds = new ConcurrentLinkedQueue();

    public MTProto(AbsMTProtoState state, MTProtoCallback callback, CallWrapper callWrapper, int connectionsCount) {
        this.state = state;
        this.connectionRate = new TransportRate(state.getAvailableConnections());
        this.callback = callback;
        this.authKey = state.getAuthKey();
        this.authKeyId = CryptoUtils.substring(CryptoUtils.SHA1(this.authKey), 12, 8);
        this.protoContext = new MTProtoContext();
        this.desiredConnectionCount = connectionsCount;
        this.session = Entropy.getInstance().generateSeed(8);
        this.tcpListener = new TcpListener();
        this.scheduller = new Scheduller(this, callWrapper);
        this.schedullerThread = new SchedullerThread();
        this.schedullerThread.start();
        this.responseProcessor = new ResponseProcessor();
        this.responseProcessor.start();
        this.connectionFixerThread = new ConnectionFixerThread();
        this.connectionFixerThread.start();
    }

    public static int readInt(byte[] src) {
        return MTProto.readInt(src, 0);
    }

    public static int readInt(byte[] src, int offset) {
        int a = src[offset + 0] & 255;
        int b = src[offset + 1] & 255;
        int c = src[offset + 2] & 255;
        int d = src[offset + 3] & 255;
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public void resetNetworkBackoff() {
        this.exponentalBackoff.reset();
    }

    public void reloadConnectionInformation() {
        this.connectionRate = new TransportRate(this.state.getAvailableConnections());
    }

    public int getInstanceIndex() {
        return this.INSTANCE_INDEX;
    }

    public String toString() {
        return "mtproto#" + this.INSTANCE_INDEX;
    }

    public void close() {
        if (!this.isClosed) {
            this.isClosed = true;
            if (this.connectionFixerThread != null) {
                this.connectionFixerThread.interrupt();
            }
            if (this.schedullerThread != null) {
                this.schedullerThread.interrupt();
            }
            if (this.responseProcessor != null) {
                this.responseProcessor.interrupt();
            }
            this.closeConnections();
        }
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeConnections() {
        HashSet<TcpContext> hashSet = this.contexts;
        synchronized (hashSet) {
            for (TcpContext context : this.contexts) {
                context.suspendConnection(true);
                this.scheduller.onConnectionDies(context.getContextId());
            }
            this.contexts.clear();
            this.contexts.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean needProcessing(long messageId) {
        ArrayList<Long> arrayList = this.receivedMessages;
        synchronized (arrayList) {
            if (this.receivedMessages.size() > 20) {
                boolean isSmallest = true;
                for (Long l : this.receivedMessages) {
                    if (messageId <= l) continue;
                    isSmallest = false;
                    break;
                }
                if (isSmallest) {
                    return false;
                }
            }
            while (this.receivedMessages.size() >= 2999) {
                this.receivedMessages.remove(0);
            }
            this.receivedMessages.add(messageId);
        }
        return true;
    }

    public void forgetMessage(int id) {
        this.scheduller.forgetMessage(id);
    }

    public int sendRpcMessage(TLMethod request, long timeout, boolean highPriority) {
        return this.sendMessage(request, timeout, true, highPriority);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int sendMessage(TLObject request, long timeout, boolean isRpc, boolean highPriority) {
        int id = this.scheduller.postMessage(request, isRpc, timeout, highPriority);
        Logger.d(this.TAG, "sendMessage #" + id + " " + request.toString());
        Scheduller scheduller = this.scheduller;
        synchronized (scheduller) {
            this.scheduller.notifyAll();
        }
        return id;
    }

    private void onMTMessage(MTMessage mtMessage) {
        if (this.futureSaltsRequestedTime - System.nanoTime() > 1800000000L) {
            Logger.d(this.TAG, "Salt check timeout");
            int count = this.state.maximumCachedSalts(TimeUtil.getUnixTime(mtMessage.getMessageId()));
            if (count < 5) {
                Logger.d(this.TAG, "Too fiew actual salts: " + count + ", requesting news");
                this.futureSaltsRequestId = this.scheduller.postMessage(new MTGetFutureSalts(64), false, 1800000L);
                this.futureSaltsRequestedTime = System.nanoTime();
            }
        }
        if (mtMessage.getSeqNo() % 2 == 1) {
            this.scheduller.confirmMessage(mtMessage.getMessageId());
        }
        if (!this.needProcessing(mtMessage.getMessageId())) {
            Logger.w(this.TAG, "Ignoring messages #" + mtMessage.getMessageId());
            try {
                Logger.w(this.TAG, "Ingoring message " + this.protoContext.deserializeMessage(new ByteArrayInputStream(mtMessage.getContent())));
            }
            catch (Exception e) {
                Logger.e(this.TAG, e);
            }
            return;
        }
        try {
            TLObject intMessage = this.protoContext.deserializeMessage(new ByteArrayInputStream(mtMessage.getContent()));
            this.onMTProtoMessage(mtMessage.getMessageId(), intMessage);
        }
        catch (DeserializeException e) {
            this.onApiMessage(mtMessage.getContent());
        }
        catch (IOException e) {
            Logger.e(this.TAG, e);
        }
    }

    private void onApiMessage(byte[] data) {
        this.callback.onApiMessage(data, this);
    }

    private void onMTProtoMessage(long msgId, TLObject object) {
        Logger.d(this.TAG, "MTProtoMessage: " + object.toString());
        if (object instanceof MTBadMessage) {
            MTBadMessage badMessage = (MTBadMessage)object;
            Logger.d(this.TAG, "BadMessage: " + badMessage.getErrorCode() + " #" + badMessage.getBadMsgId());
            this.scheduller.confirmMessage(badMessage.getBadMsgId());
            this.scheduller.onMessageConfirmed(badMessage.getBadMsgId());
            long time = this.scheduller.getMessageIdGenerationTime(badMessage.getBadMsgId());
            if (time != 0L) {
                if (badMessage.getErrorCode() == 17 || badMessage.getErrorCode() == 16) {
                    long delta = System.nanoTime() / 1000000L - time;
                    TimeOverlord.getInstance().onForcedServerTimeArrived((msgId >> 32) * 1000L, delta);
                    if (badMessage.getErrorCode() == 17) {
                        this.scheduller.resetMessageId();
                    }
                    this.scheduller.resendAsNewMessage(badMessage.getBadMsgId());
                    this.requestSchedule();
                } else if (badMessage.getErrorCode() == 33 || badMessage.getErrorCode() == 32) {
                    if (this.scheduller.isMessageFromCurrentGeneration(badMessage.getBadMsgId())) {
                        Logger.d(this.TAG, "Resetting session");
                        this.session = Entropy.getInstance().generateSeed(8);
                        this.scheduller.resetSession();
                    }
                    this.scheduller.resendAsNewMessage(badMessage.getBadMsgId());
                    this.requestSchedule();
                } else if (badMessage.getErrorCode() == 48) {
                    long salt = badMessage.getNewServerSalt();
                    long delta = System.nanoTime() / 1000000L - time;
                    TimeOverlord.getInstance().onMethodExecuted(badMessage.getBadMsgId(), msgId, delta);
                    this.state.badServerSalt(salt);
                    Logger.d(this.TAG, "Reschedule messages because bad_server_salt #" + badMessage.getBadMsgId());
                    this.scheduller.resendAsNewMessage(badMessage.getBadMsgId());
                    this.requestSchedule();
                } else if (badMessage.getErrorCode() == 64 || badMessage.getErrorCode() == 19) {
                    this.scheduller.resendMessage(badMessage.getBadMsgId());
                    this.requestSchedule();
                } else if (badMessage.getErrorCode() == 20) {
                    this.scheduller.resendAsNewMessage(badMessage.getBadMsgId());
                    this.requestSchedule();
                } else {
                    Logger.d(this.TAG, "Ignored BadMsg #" + badMessage.getErrorCode() + " (" + badMessage.getBadMsgId() + ", " + badMessage.getBadMsqSeqno() + ")");
                    this.scheduller.forgetMessageByMsgId(badMessage.getBadMsgId());
                }
            } else {
                Logger.d(this.TAG, "Unknown package #" + badMessage.getBadMsgId());
            }
        } else if (object instanceof MTMsgsAck) {
            MTMsgsAck ack = (MTMsgsAck)object;
            String log = "";
            for (Long ackMsgId : ack.getMessages()) {
                this.scheduller.onMessageConfirmed(ackMsgId);
                if (log.length() > 0) {
                    log = log + ", ";
                }
                log = log + ackMsgId;
                int id = this.scheduller.mapSchedullerId(ackMsgId);
                if (id <= 0) continue;
                this.callback.onConfirmed(id);
            }
            Logger.d(this.TAG, "msgs_ack: " + log);
        } else if (object instanceof MTRpcResult) {
            MTRpcResult result = (MTRpcResult)object;
            Logger.d(this.TAG, "rpc_result: " + result.getMessageId());
            int id = this.scheduller.mapSchedullerId(result.getMessageId());
            if (id > 0) {
                int responseConstructor = MTProto.readInt(result.getContent());
                if (responseConstructor == 558156313) {
                    try {
                        MTRpcError error = (MTRpcError)this.protoContext.deserializeMessage(result.getContent());
                        BytesCache.getInstance().put(result.getContent());
                        if (error.getErrorCode() == 420 && error.getErrorTag().startsWith("FLOOD_WAIT_")) {
                            int delay = Integer.parseInt(error.getErrorTag().substring("FLOOD_WAIT_".length()));
                            Logger.w(this.TAG, error.getErrorTag());
                            if (delay <= 10) {
                                this.scheduller.resendAsNewMessageDelayed(result.getMessageId(), delay * 1000);
                                this.requestSchedule();
                                return;
                            }
                        }
                        if (error.getErrorCode() == 401 && (error.getErrorTag().equals("AUTH_KEY_UNREGISTERED") || error.getErrorTag().equals("AUTH_KEY_INVALID") || error.getErrorTag().equals("USER_DEACTIVATED") || error.getErrorTag().equals("SESSION_REVOKED") || error.getErrorTag().equals("SESSION_EXPIRED"))) {
                            Logger.w(this.TAG, "Auth key invalidated");
                            this.callback.onAuthInvalidated(this);
                            this.close();
                            return;
                        }
                        this.callback.onRpcError(id, error.getErrorCode(), error.getMessage(), this);
                        this.scheduller.forgetMessage(id);
                    }
                    catch (IOException e) {
                        Logger.e(this.TAG, e);
                        return;
                    }
                } else {
                    Logger.d(this.TAG, "rpc_result: " + result.getMessageId() + " #" + Integer.toHexString(responseConstructor));
                    this.callback.onRpcResult(id, result.getContent(), this);
                    BytesCache.getInstance().put(result.getContent());
                    this.scheduller.forgetMessage(id);
                }
            } else {
                Logger.d(this.TAG, "ignored rpc_result: " + result.getMessageId());
                BytesCache.getInstance().put(result.getContent());
            }
            this.scheduller.confirmMessage(result.getMessageId());
            this.scheduller.onMessageConfirmed(result.getMessageId());
            long time = this.scheduller.getMessageIdGenerationTime(result.getMessageId());
            if (time != 0L) {
                long delta = System.nanoTime() / 1000000L - time;
                TimeOverlord.getInstance().onMethodExecuted(result.getMessageId(), msgId, delta);
            }
        } else if (object instanceof MTPong) {
            MTPong pong = (MTPong)object;
            Logger.d(this.TAG, "pong: " + pong.getPingId());
            this.scheduller.onMessageConfirmed(pong.getMessageId());
            this.scheduller.forgetMessageByMsgId(pong.getMessageId());
            long time = this.scheduller.getMessageIdGenerationTime(pong.getMessageId());
            if (time != 0L) {
                long delta = System.nanoTime() / 1000000L - time;
                TimeOverlord.getInstance().onMethodExecuted(pong.getMessageId(), msgId, delta);
            }
        } else if (object instanceof MTFutureSalts) {
            MTFutureSalts salts = (MTFutureSalts)object;
            this.scheduller.onMessageConfirmed(salts.getRequestId());
            this.scheduller.forgetMessageByMsgId(salts.getRequestId());
            long time = this.scheduller.getMessageIdGenerationTime(salts.getRequestId());
            if (time > 0L) {
                KnownSalt[] knownSalts = new KnownSalt[salts.getSalts().size()];
                for (int i = 0; i < knownSalts.length; ++i) {
                    MTFutureSalt salt = salts.getSalts().get(i);
                    knownSalts[i] = new KnownSalt(salt.getValidSince(), salt.getValidUntil(), salt.getSalt());
                }
                long delta = System.nanoTime() / 1000000L - time;
                TimeOverlord.getInstance().onForcedServerTimeArrived(salts.getNow(), delta);
                this.state.mergeKnownSalts(salts.getNow(), knownSalts);
            }
        } else if (object instanceof MTMessageDetailedInfo) {
            MTMessageDetailedInfo detailedInfo = (MTMessageDetailedInfo)object;
            Logger.d(this.TAG, "msg_detailed_info: " + detailedInfo.getMsgId() + ", answer: " + detailedInfo.getAnswerMsgId());
            if (this.receivedMessages.contains(detailedInfo.getAnswerMsgId())) {
                this.scheduller.confirmMessage(detailedInfo.getAnswerMsgId());
            } else {
                int id = this.scheduller.mapSchedullerId(detailedInfo.getMsgId());
                if (id > 0) {
                    this.scheduller.postMessage(new MTNeedResendMessage(new long[]{detailedInfo.getAnswerMsgId()}), false, 60000L);
                } else {
                    this.scheduller.confirmMessage(detailedInfo.getAnswerMsgId());
                    this.scheduller.forgetMessageByMsgId(detailedInfo.getMsgId());
                }
            }
        } else if (object instanceof MTNewMessageDetailedInfo) {
            MTNewMessageDetailedInfo detailedInfo = (MTNewMessageDetailedInfo)object;
            Logger.d(this.TAG, "msg_new_detailed_info: " + detailedInfo.getAnswerMsgId());
            if (this.receivedMessages.contains(detailedInfo.getAnswerMsgId())) {
                this.scheduller.confirmMessage(detailedInfo.getAnswerMsgId());
            } else {
                this.scheduller.postMessage(new MTNeedResendMessage(new long[]{detailedInfo.getAnswerMsgId()}), false, 60000L);
            }
        } else if (object instanceof MTNewSessionCreated) {
            MTNewSessionCreated newSessionCreated = (MTNewSessionCreated)object;
            if (!this.newSessionsIds.contains(newSessionCreated.getUniqId())) {
                KnownSalt[] knownSalts = new KnownSalt[1];
                int validSince = (int)System.currentTimeMillis() / 1000;
                knownSalts[0] = new KnownSalt(validSince, validSince + 1800, ((MTNewSessionCreated)object).getServerSalt());
                this.state.mergeKnownSalts(validSince, knownSalts);
            }
            this.scheduller.updateMessageId(((MTNewSessionCreated)object).getFirstMsgId());
            this.callback.onSessionCreated(this);
        } else {
            this.scheduller.onMessageConfirmed(msgId);
            Logger.w(this.TAG, "Ignored MTProto message " + object.toString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void internalSchedule() {
        long time = System.nanoTime() / 1000000L;
        if (time - this.lastPingTime > 60000L) {
            this.lastPingTime = time;
            HashSet<TcpContext> hashSet = this.contexts;
            synchronized (hashSet) {
                for (TcpContext context : this.contexts) {
                    this.scheduller.postMessageDelayed(new MTPingDelayDisconnect(Entropy.getInstance().generateRandomId(), 75), false, 60000L, 0, context.getContextId(), false);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void requestSchedule() {
        Scheduller scheduller = this.scheduller;
        synchronized (scheduller) {
            this.scheduller.notifyAll();
        }
    }

    private EncryptedMessage encrypt(int seqNo, long messageId, byte[] content) throws IOException {
        long salt = this.state.findActualSalt((int)(TimeOverlord.getInstance().getServerTime() / 1000L));
        ByteArrayOutputStream messageBody = new ByteArrayOutputStream();
        StreamingUtils.writeLong(salt, messageBody);
        StreamingUtils.writeByteArray(this.session, messageBody);
        StreamingUtils.writeLong(messageId, messageBody);
        StreamingUtils.writeInt(seqNo, messageBody);
        StreamingUtils.writeInt(content.length, messageBody);
        StreamingUtils.writeByteArray(content, messageBody);
        byte[] innerData = messageBody.toByteArray();
        byte[] msgKey = CryptoUtils.substring(CryptoUtils.SHA1(innerData), 4, 16);
        int fastConfirm = MTProto.readInt(CryptoUtils.SHA1(innerData)) | Integer.MIN_VALUE;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamingUtils.writeByteArray(this.authKeyId, out);
        StreamingUtils.writeByteArray(msgKey, out);
        byte[] sha1_a = CryptoUtils.SHA1(msgKey, CryptoUtils.substring(this.authKey, 0, 32));
        byte[] sha1_b = CryptoUtils.SHA1(CryptoUtils.substring(this.authKey, 32, 16), msgKey, CryptoUtils.substring(this.authKey, 48, 16));
        byte[] sha1_c = CryptoUtils.SHA1(CryptoUtils.substring(this.authKey, 64, 32), msgKey);
        byte[] sha1_d = CryptoUtils.SHA1(msgKey, CryptoUtils.substring(this.authKey, 96, 32));
        byte[] aesKey = CryptoUtils.concat(CryptoUtils.substring(sha1_a, 0, 8), CryptoUtils.substring(sha1_b, 8, 12), CryptoUtils.substring(sha1_c, 4, 12));
        byte[] aesIv = CryptoUtils.concat(CryptoUtils.substring(sha1_a, 8, 12), CryptoUtils.substring(sha1_b, 0, 8), CryptoUtils.substring(sha1_c, 16, 4), CryptoUtils.substring(sha1_d, 0, 8));
        byte[] encoded = CryptoUtils.AES256IGEEncrypt(CryptoUtils.align(innerData, 16), aesIv, aesKey);
        StreamingUtils.writeByteArray(encoded, out);
        EncryptedMessage res = new EncryptedMessage();
        res.data = out.toByteArray();
        res.fastConfirm = fastConfirm;
        return res;
    }

    private byte[] optimizedSHA(byte[] serverSalt, byte[] session, long msgId, int seq, int len, byte[] data, int datalen) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(serverSalt);
            crypt.update(session);
            crypt.update(StreamingUtils.longToBytes(msgId));
            crypt.update(StreamingUtils.intToBytes(seq));
            crypt.update(StreamingUtils.intToBytes(len));
            crypt.update(data, 0, datalen);
            return crypt.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private MTMessage decrypt(byte[] data, int offset, int len) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(offset);
        byte[] msgAuthKey = StreamingUtils.readBytes(8, stream);
        for (int i = 0; i < this.authKeyId.length; ++i) {
            if (msgAuthKey[i] == this.authKeyId[i]) continue;
            Logger.w(this.TAG, "Unsupported msgAuthKey");
            throw new SecurityException();
        }
        byte[] msgKey = StreamingUtils.readBytes(16, stream);
        byte[] sha1_a = CryptoUtils.SHA1(msgKey, CryptoUtils.substring(this.authKey, 8, 32));
        byte[] sha1_b = CryptoUtils.SHA1(CryptoUtils.substring(this.authKey, 40, 16), msgKey, CryptoUtils.substring(this.authKey, 56, 16));
        byte[] sha1_c = CryptoUtils.SHA1(CryptoUtils.substring(this.authKey, 72, 32), msgKey);
        byte[] sha1_d = CryptoUtils.SHA1(msgKey, CryptoUtils.substring(this.authKey, 104, 32));
        byte[] aesKey = CryptoUtils.concat(CryptoUtils.substring(sha1_a, 0, 8), CryptoUtils.substring(sha1_b, 8, 12), CryptoUtils.substring(sha1_c, 4, 12));
        byte[] aesIv = CryptoUtils.concat(CryptoUtils.substring(sha1_a, 8, 12), CryptoUtils.substring(sha1_b, 0, 8), CryptoUtils.substring(sha1_c, 16, 4), CryptoUtils.substring(sha1_d, 0, 8));
        int totalLen = len - 8 - 16;
        byte[] encMessage = BytesCache.getInstance().allocate(totalLen);
        StreamingUtils.readBytes(encMessage, 0, totalLen, stream);
        byte[] rawMessage = BytesCache.getInstance().allocate(totalLen);
        long decryptStart = System.currentTimeMillis();
        CryptoUtils.AES256IGEDecryptBig(encMessage, rawMessage, totalLen, aesIv, aesKey);
        Logger.d(this.TAG, "Decrypted in " + (System.currentTimeMillis() - decryptStart) + " ms");
        BytesCache.getInstance().put(encMessage);
        ByteArrayInputStream bodyStream = new ByteArrayInputStream(rawMessage);
        byte[] serverSalt = StreamingUtils.readBytes(8, bodyStream);
        byte[] session = StreamingUtils.readBytes(8, bodyStream);
        long messageId = StreamingUtils.readLong(bodyStream);
        int mes_seq = StreamingUtils.readInt(bodyStream);
        int msg_len = StreamingUtils.readInt(bodyStream);
        int bodySize = totalLen - 32;
        if (msg_len % 4 != 0) {
            throw new SecurityException();
        }
        if (msg_len > bodySize) {
            throw new SecurityException();
        }
        if (msg_len - bodySize > 15) {
            throw new SecurityException();
        }
        byte[] message = BytesCache.getInstance().allocate(msg_len);
        StreamingUtils.readBytes(message, 0, msg_len, bodyStream);
        BytesCache.getInstance().put(rawMessage);
        byte[] checkHash = this.optimizedSHA(serverSalt, session, messageId, mes_seq, msg_len, message, msg_len);
        if (!CryptoUtils.arrayEq(CryptoUtils.substring(checkHash, 4, 16), msgKey)) {
            throw new SecurityException();
        }
        if (!CryptoUtils.arrayEq(session, this.session)) {
            return null;
        }
        if (TimeOverlord.getInstance().getTimeAccuracy() < 10L) {
            long time = messageId >> 32;
            long serverTime = TimeOverlord.getInstance().getServerTime() / 1000L;
            if (serverTime + 30L < time) {
                Logger.w(this.TAG, "Incorrect message (" + messageId + ") time: " + time + " with server time: " + serverTime);
            }
            if (time < serverTime - 300L) {
                Logger.w(this.TAG, "Incorrect message (" + messageId + ") time: " + time + " with server time: " + serverTime);
            }
        }
        return new MTMessage(messageId, mes_seq, message, message.length);
    }

    private class EncryptedMessage {
        public byte[] data;
        public int fastConfirm;

        private EncryptedMessage() {
        }
    }

    private class TcpListener
    implements TcpContextCallback {
        private TcpListener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onRawMessage(byte[] data, int offset, int len, TcpContext context) {
            block26 : {
                if (MTProto.this.isClosed) {
                    return;
                }
                try {
                    MTMessage decrypted = MTProto.this.decrypt(data, offset, len);
                    if (decrypted == null) {
                        Logger.d(MTProto.this.TAG, "message ignored");
                        return;
                    }
                    if (!MTProto.this.connectedContexts.contains(context.getContextId())) {
                        MTProto.this.connectedContexts.add(context.getContextId());
                        MTProto.this.exponentalBackoff.onSuccess();
                        MTProto.this.connectionRate.onConnectionSuccess((Integer)MTProto.this.contextConnectionId.get(context.getContextId()));
                    }
                    Logger.d(MTProto.this.TAG, "MessageArrived (#" + context.getContextId() + "): time: " + TimeUtil.getUnixTime(decrypted.getMessageId()));
                    Logger.d(MTProto.this.TAG, "MessageArrived (#" + context.getContextId() + "): seqNo: " + decrypted.getSeqNo() + ", msgId:" + decrypted.getMessageId());
                    int messageClass = MTProto.readInt(decrypted.getContent());
                    if (messageClass == 1945237724) {
                        try {
                            TLObject object = MTProto.this.protoContext.deserializeMessage(new ByteArrayInputStream(decrypted.getContent()));
                            if (object instanceof MTMessagesContainer) {
                                for (MTMessage mtMessage : ((MTMessagesContainer)object).getMessages()) {
                                    MTProto.this.inQueue.add(mtMessage);
                                }
                                ConcurrentLinkedQueue concurrentLinkedQueue = MTProto.this.inQueue;
                                synchronized (concurrentLinkedQueue) {
                                    MTProto.this.inQueue.notifyAll();
                                }
                            }
                            BytesCache.getInstance().put(decrypted.getContent());
                        }
                        catch (DeserializeException e) {
                            Logger.e(MTProto.this.TAG, e);
                        }
                        break block26;
                    }
                    if (messageClass == -530561358) {
                        Logger.w(MTProto.this.TAG, "On msg copy");
                        try {
                            TLObject object = MTProto.this.protoContext.deserializeMessage(new ByteArrayInputStream(decrypted.getContent()));
                            MTMessageCopy messageCopy = (MTMessageCopy)object;
                            MTProto.this.scheduller.confirmMessage(decrypted.getMessageId());
                            MTProto.this.inQueue.add(messageCopy.getOrig_message());
                            ConcurrentLinkedQueue mtMessage = MTProto.this.inQueue;
                            synchronized (mtMessage) {
                                MTProto.this.inQueue.notifyAll();
                                break block26;
                            }
                        }
                        catch (DeserializeException e) {
                            Logger.e(MTProto.this.TAG, e);
                        }
                        break block26;
                    }
                    MTProto.this.inQueue.add(decrypted);
                    ConcurrentLinkedQueue e = MTProto.this.inQueue;
                    synchronized (e) {
                        MTProto.this.inQueue.notifyAll();
                    }
                }
                catch (IOException e) {
                    Logger.e(MTProto.this.TAG, e);
                    HashSet messageClass = MTProto.this.contexts;
                    synchronized (messageClass) {
                        context.suspendConnection(true);
                        if (!MTProto.this.connectedContexts.contains(context.getContextId())) {
                            MTProto.this.exponentalBackoff.onFailureNoWait();
                            MTProto.this.connectionRate.onConnectionFailure((Integer)MTProto.this.contextConnectionId.get(context.getContextId()));
                        }
                        MTProto.this.contexts.remove(context);
                        MTProto.this.contexts.notifyAll();
                        MTProto.this.scheduller.onConnectionDies(context.getContextId());
                    }
                }
            }
        }

        @Override
        public void onError(int errorCode, TcpContext context) {
            if (MTProto.this.isClosed) {
                return;
            }
            Logger.w(MTProto.this.TAG, "OnError (#" + context.getContextId() + "): " + errorCode);
            context.suspendConnection(true);
            context.connect();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onChannelBroken(TcpContext context) {
            if (MTProto.this.isClosed) {
                return;
            }
            int contextId = context.getContextId();
            Logger.d(MTProto.this.TAG, "onChannelBroken (#" + contextId + ")");
            HashSet hashSet = MTProto.this.contexts;
            synchronized (hashSet) {
                MTProto.this.contexts.remove(context);
                if (!MTProto.this.connectedContexts.contains(contextId) && MTProto.this.contextConnectionId.containsKey(contextId)) {
                    MTProto.this.exponentalBackoff.onFailureNoWait();
                    MTProto.this.connectionRate.onConnectionFailure((Integer)MTProto.this.contextConnectionId.get(contextId));
                }
                MTProto.this.contexts.notifyAll();
            }
            MTProto.this.scheduller.onConnectionDies(context.getContextId());
            MTProto.this.requestSchedule();
        }

        @Override
        public void onFastConfirm(int hash) {
            int[] ids;
            if (MTProto.this.isClosed) {
                return;
            }
            MTProto.this.scheduller.onMessageFastConfirmed(hash);
            for (int id : ids = MTProto.this.scheduller.mapFastConfirm(hash)) {
                MTProto.this.callback.onConfirmed(id);
            }
        }
    }

    private class ConnectionFixerThread
    extends Thread {
        private ConnectionFixerThread() {
            this.setName("ConnectionFixerThread#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            while (!MTProto.this.isClosed) {
                Logger.d(MTProto.this.TAG, "Connection Fixer Iteration");
                HashSet hashSet = MTProto.this.contexts;
                synchronized (hashSet) {
                    if (MTProto.this.contexts.size() >= MTProto.this.desiredConnectionCount) {
                        try {
                            MTProto.this.contexts.wait();
                        }
                        catch (InterruptedException e) {
                            return;
                        }
                    }
                }
                ConnectionType type = MTProto.this.connectionRate.tryConnection();
                TcpContext context = new TcpContext(MTProto.this, type.getHost(), type.getPort(), MTProto.this.tcpListener);
                context.connect();
                if (MTProto.this.isClosed) {
                    return;
                }
                MTProto.this.scheduller.postMessageDelayed(new MTPing(Entropy.getInstance().generateRandomId()), false, 60000L, 0, context.getContextId(), false);
                Object object = MTProto.this.contexts;
                synchronized (object) {
                    MTProto.this.contexts.add(context);
                    MTProto.this.contextConnectionId.put(context.getContextId(), type.getId());
                }
                object = MTProto.this.scheduller;
                synchronized (object) {
                    MTProto.this.scheduller.notifyAll();
                }
            }
        }
    }

    private class ResponseProcessor
    extends Thread {
        public ResponseProcessor() {
            this.setName("ResponseProcessor#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            while (!MTProto.this.isClosed) {
                Logger.d(MTProto.this.TAG, "Response Iteration");
                ConcurrentLinkedQueue concurrentLinkedQueue = MTProto.this.inQueue;
                synchronized (concurrentLinkedQueue) {
                    if (MTProto.this.inQueue.isEmpty()) {
                        try {
                            MTProto.this.inQueue.wait();
                        }
                        catch (InterruptedException e) {
                            return;
                        }
                    }
                    if (MTProto.this.inQueue.isEmpty()) {
                        continue;
                    }
                }
                MTMessage message = (MTMessage)MTProto.this.inQueue.poll();
                MTProto.this.onMTMessage(message);
                BytesCache.getInstance().put(message.getContent());
            }
        }
    }

    private class SchedullerThread
    extends Thread {
        private SchedullerThread() {
            this.setName("Scheduller#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            PrepareSchedule prepareSchedule = new PrepareSchedule();
            while (!MTProto.this.isClosed) {
                int[] contextIds;
                Logger.d(MTProto.this.TAG, "Scheduller Iteration");
                Object object = MTProto.this.contexts;
                synchronized (object) {
                    TcpContext[] currentContexts = MTProto.this.contexts.toArray(new TcpContext[0]);
                    contextIds = new int[currentContexts.length];
                    for (int i = 0; i < contextIds.length; ++i) {
                        contextIds[i] = currentContexts[i].getContextId();
                    }
                }
                object = MTProto.this.scheduller;
                synchronized (object) {
                    MTProto.this.scheduller.prepareScheduller(prepareSchedule, contextIds);
                    if (prepareSchedule.isDoWait()) {
                        Logger.d(MTProto.this.TAG, "Scheduller:wait " + prepareSchedule.getDelay());
                        try {
                            MTProto.this.scheduller.wait(Math.min(prepareSchedule.getDelay(), 30000L));
                        }
                        catch (InterruptedException e) {
                            Logger.e(MTProto.this.TAG, e);
                            return;
                        }
                        MTProto.this.internalSchedule();
                        continue;
                    }
                }
                TcpContext context = null;
                Object e = MTProto.this.contexts;
                synchronized (e) {
                    TcpContext[] currentContexts = MTProto.this.contexts.toArray(new TcpContext[0]);
                    block18 : for (int i = 0; i < currentContexts.length; ++i) {
                        int index = (i + MTProto.this.roundRobin + 1) % currentContexts.length;
                        for (int allowed : prepareSchedule.getAllowedContexts()) {
                            if (currentContexts[index].getContextId() != allowed) continue;
                            context = currentContexts[index];
                            break block18;
                        }
                    }
                    if (currentContexts.length != 0) {
                        MTProto.this.roundRobin = (MTProto.this.roundRobin + 1) % currentContexts.length;
                    }
                }
                if (context == null) {
                    Logger.d(MTProto.this.TAG, "Scheduller: no context");
                    continue;
                }
                Logger.d(MTProto.this.TAG, "doSchedule");
                MTProto.this.internalSchedule();
                e = MTProto.this.scheduller;
                synchronized (e) {
                    long start = System.currentTimeMillis();
                    PreparedPackage preparedPackage = MTProto.this.scheduller.doSchedule(context.getContextId(), MTProto.this.initedContext.contains(context.getContextId()));
                    Logger.d(MTProto.this.TAG, "Schedulled in " + (System.currentTimeMillis() - start) + " ms");
                    if (preparedPackage == null) {
                        continue;
                    }
                    Logger.d(MTProto.this.TAG, "MessagePushed (#" + context.getContextId() + "): time:" + TimeUtil.getUnixTime(preparedPackage.getMessageId()));
                    Logger.d(MTProto.this.TAG, "MessagePushed (#" + context.getContextId() + "): seqNo:" + preparedPackage.getSeqNo() + ", msgId" + preparedPackage.getMessageId());
                    try {
                        EncryptedMessage msg = MTProto.this.encrypt(preparedPackage.getSeqNo(), preparedPackage.getMessageId(), preparedPackage.getContent());
                        if (preparedPackage.isHighPriority()) {
                            MTProto.this.scheduller.registerFastConfirm(preparedPackage.getMessageId(), msg.fastConfirm);
                        }
                        context.postMessage(msg.data, preparedPackage.isHighPriority());
                        MTProto.this.initedContext.add(context.getContextId());
                    }
                    catch (IOException e2) {
                        Logger.e(MTProto.this.TAG, e2);
                    }
                }
            }
        }
    }

}

