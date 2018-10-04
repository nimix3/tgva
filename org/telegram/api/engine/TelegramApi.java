
package org.telegram.api.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.api.TLApiContext;
import org.telegram.api.TLConfig;
import org.telegram.api.auth.TLExportedAuthorization;
import org.telegram.api.engine.ApiCallback;
import org.telegram.api.engine.AppInfo;
import org.telegram.api.engine.GzipRequest;
import org.telegram.api.engine.Logger;
import org.telegram.api.engine.RpcCallback;
import org.telegram.api.engine.RpcCallbackEx;
import org.telegram.api.engine.RpcException;
import org.telegram.api.engine.TimeoutException;
import org.telegram.api.engine.file.Downloader;
import org.telegram.api.engine.file.Uploader;
import org.telegram.api.engine.storage.AbsApiState;
import org.telegram.api.functions.TLRequestInitConnection;
import org.telegram.api.functions.TLRequestInvokeWithLayer;
import org.telegram.api.functions.auth.TLRequestAuthExportAuthorization;
import org.telegram.api.functions.auth.TLRequestAuthImportAuthorization;
import org.telegram.api.functions.help.TLRequestHelpGetConfig;
import org.telegram.api.functions.upload.TLRequestUploadGetFile;
import org.telegram.api.functions.upload.TLRequestUploadSaveBigFilePart;
import org.telegram.api.functions.upload.TLRequestUploadSaveFilePart;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.api.upload.TLFile;
import org.telegram.mtproto.CallWrapper;
import org.telegram.mtproto.MTProto;
import org.telegram.mtproto.MTProtoCallback;
import org.telegram.mtproto.pq.Authorizer;
import org.telegram.mtproto.pq.PqAuth;
import org.telegram.mtproto.state.AbsMTProtoState;
import org.telegram.mtproto.state.ConnectionInfo;
import org.telegram.mtproto.util.BytesCache;
import org.telegram.tl.TLBool;
import org.telegram.tl.TLBoolTrue;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class TelegramApi {
    private static final AtomicInteger rpcCallIndex = new AtomicInteger(0);
    private static final AtomicInteger instanceIndex = new AtomicInteger(1000);
    private static final int CHANNELS_MAIN = 1;
    private static final int CHANNELS_FS = 2;
    private static final int DEFAULT_TIMEOUT_CHECK = 15000;
    private static final int DEFAULT_TIMEOUT = 15000;
    private static final int FILE_TIMEOUT = 45000;
    private final String TAG = "TelegramApi#" + this.INSTANCE_INDEX;
    private final int INSTANCE_INDEX = instanceIndex.incrementAndGet();
    private final HashMap<Integer, MTProto> dcProtos = new HashMap();
    private final HashMap<Integer, Object> dcSync = new HashMap();
    private final HashMap<Integer, RpcCallbackWrapper> callbacks = new HashMap();
    private final HashMap<Integer, Integer> sentRequests = new HashMap();
    private final TreeMap<Long, Integer> timeoutTimes = new TreeMap();
    private final TreeMap<Integer, Boolean> dcRequired = new TreeMap();
    private static final int DEFAULTCOMPETABLETIMEOUTMILLIS = 20000;
    private boolean isClosed;
    private int primaryDc;
    private MTProto mainProto;
    private ProtoCallback callback;
    private SenderThread senderThread;
    private TLApiContext apiContext;
    private TimeoutThread timeoutThread;
    private ConnectionThread dcThread;
    private HashSet<Integer> registeredInApi = new HashSet();
    public AbsApiState state;
    private AppInfo appInfo;
    private ApiCallback apiCallback;
    private Downloader downloader;
    private Uploader uploader;

    public TelegramApi(AbsApiState state, AppInfo _appInfo, ApiCallback _apiCallback) {
        long start = System.currentTimeMillis();
        this.apiCallback = _apiCallback;
        this.appInfo = _appInfo;
        this.state = state;
        this.primaryDc = state.getPrimaryDc();
        this.isClosed = false;
        this.callback = new ProtoCallback();
        Logger.d(this.TAG, "Phase 0 in " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        this.apiContext = new TLApiContext(){
            private AtomicInteger integer = new AtomicInteger(0);

            @Override
            public TLObject deserializeMessage(int clazzId, InputStream stream) throws IOException {
                if (this.integer.incrementAndGet() % 10 == 9) {
                    Thread.yield();
                }
                return super.deserializeMessage(clazzId, stream);
            }

            @Override
            public TLBytes allocateBytes(int size) {
                return new TLBytes(BytesCache.getInstance().allocate(size), 0, size);
            }

            @Override
            public void releaseBytes(TLBytes unused) {
                BytesCache.getInstance().put(unused.getData());
            }
        };
        Logger.d(this.TAG, "Phase 1 in " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        this.timeoutThread = new TimeoutThread();
        this.timeoutThread.start();
        this.dcThread = new ConnectionThread();
        this.dcThread.start();
        this.senderThread = new SenderThread();
        this.senderThread.start();
        Logger.d(this.TAG, "Phase 2 in " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        this.downloader = new Downloader(this);
        this.uploader = new Uploader(this);
        Logger.d(this.TAG, "Phase 3 in " + (System.currentTimeMillis() - start) + " ms");
    }

    public Downloader getDownloader() {
        return this.downloader;
    }

    public Uploader getUploader() {
        return this.uploader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void switchToDc(int dcId) {
        if (this.mainProto != null) {
            this.mainProto.close();
        }
        this.mainProto = null;
        this.primaryDc = dcId;
        this.state.setPrimaryDc(dcId);
        TreeMap<Integer, Boolean> treeMap = this.dcRequired;
        synchronized (treeMap) {
            this.dcRequired.notifyAll();
        }
    }

    public String toString() {
        return "api#" + this.INSTANCE_INDEX;
    }

    private TLMethod wrapForDc(int dcId, TLMethod method) {
        if (this.registeredInApi.contains(dcId)) {
            return new TLRequestInvokeWithLayer(method);
        }
        return new TLRequestInvokeWithLayer(new TLRequestInitConnection(this.appInfo.getApiId(), this.appInfo.getDeviceModel(), this.appInfo.getSystemVersion(), this.appInfo.getAppVersion(), this.appInfo.getLangCode(), method));
    }

    public AbsApiState getState() {
        return this.state;
    }

    public TLApiContext getApiContext() {
        return this.apiContext;
    }

    protected void onMessageArrived(TLObject object) {
        if (object instanceof TLAbsUpdates) {
            Logger.d(this.TAG, "<< update " + object.toString());
            this.apiCallback.onUpdate((TLAbsUpdates)object);
        } else {
            Logger.d(this.TAG, "<< unknown object " + object.toString());
        }
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void close() {
        if (!this.isClosed) {
            this.apiCallback.onAuthCancelled(this);
            this.isClosed = true;
            if (this.timeoutThread != null) {
                this.timeoutThread.interrupt();
                this.timeoutThread = null;
            }
            this.mainProto.close();
        }
    }

    public void resetNetworkBackoff() {
        if (this.mainProto != null) {
            this.mainProto.resetNetworkBackoff();
        }
        for (MTProto mtProto : this.dcProtos.values()) {
            mtProto.resetNetworkBackoff();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetConnectionInfo() {
        this.mainProto.reloadConnectionInformation();
        HashMap<Integer, MTProto> hashMap = this.dcProtos;
        synchronized (hashMap) {
            for (MTProto proto : this.dcProtos.values()) {
                proto.reloadConnectionInformation();
            }
        }
    }

    private <T extends TLObject> void doRpcCall(TLMethod<T> method, int timeout, RpcCallback<T> callback, int destDc) {
        this.doRpcCall(method, timeout, callback, destDc, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T extends TLObject> void doRpcCall(TLMethod<T> method, int timeout, RpcCallback<T> callback, int destDc, boolean authRequired) {
        if (this.isClosed) {
            if (callback != null) {
                callback.onError(0, null);
            }
            return;
        }
        int localRpcId = rpcCallIndex.getAndIncrement();
        HashMap<Integer, RpcCallbackWrapper> hashMap = this.callbacks;
        synchronized (hashMap) {
            RpcCallbackWrapper wrapper = new RpcCallbackWrapper(localRpcId, method, callback);
            wrapper.dcId = destDc;
            wrapper.timeout = timeout;
            wrapper.isAuthRequred = authRequired;
            this.callbacks.put(localRpcId, wrapper);
            if (callback != null) {
                long timeoutTime = System.nanoTime() + (long)(timeout * 2 * 1000) * 1000L;
                TreeMap<Long, Integer> treeMap = this.timeoutTimes;
                synchronized (treeMap) {
                    while (this.timeoutTimes.containsKey(timeoutTime)) {
                        ++timeoutTime;
                    }
                    this.timeoutTimes.put(timeoutTime, localRpcId);
                    this.timeoutTimes.notifyAll();
                }
                wrapper.timeoutTime = timeoutTime;
            }
            if (authRequired) {
                this.checkDcAuth(destDc);
            } else {
                this.checkDc(destDc);
            }
            this.callbacks.notifyAll();
        }
        Logger.d(this.TAG, ">> #" + localRpcId + ": " + method.toString());
    }

    private <T extends TLObject> T doRpcCall(TLMethod<T> method, int timeout, int destDc) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, timeout, destDc, true);
    }

    private <T extends TLObject> T doRpcCall(TLMethod<T> method, int timeout, int destDc, boolean authRequired) throws RpcException, java.util.concurrent.TimeoutException {
        TLObject resultObject;
        block5 : {
            if (this.isClosed) {
                throw new RpcException(0, "Connection is closed");
            }
            resultObject = null;
            final CompletableFuture completableFuture = new CompletableFuture();
            this.doRpcCall(method, timeout, new RpcCallback<T>(){

                @Override
                public void onResult(T result) {
                    completableFuture.complete(result);
                }

                @Override
                public void onError(int errorCode, String message) {
                    completableFuture.completeExceptionally(new RpcException(errorCode, message));
                }
            }, destDc, authRequired);
            try {
                resultObject = (TLObject)completableFuture.get(timeout, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                Logger.w(this.TAG, method.toString());
                Logger.e(this.TAG, e);
            }
            catch (java.util.concurrent.TimeoutException e) {
                Logger.w(this.TAG, method.toString());
                Logger.e(this.TAG, e);
                throw e;
            }
            catch (ExecutionException e) {
                Logger.w(this.TAG, method.toString());
                Logger.e(this.TAG, e);
                if (!(e.getCause() instanceof RpcException)) break block5;
                throw (RpcException)e.getCause();
            }
        }
        return (T)resultObject;
    }

    public <T extends TLObject> void doRpcCallWeak(TLMethod<T> method) {
        this.doRpcCallWeak(method, 15000);
    }

    public <T extends TLObject> void doRpcCallWeak(TLMethod<T> method, int timeout) {
        this.doRpcCall(method, timeout, null);
    }

    public <T extends TLObject> void doRpcCall(TLMethod<T> method, RpcCallback<T> callback) {
        this.doRpcCall(method, 15000, callback);
    }

    public <T extends TLObject> void doRpcCall(TLMethod<T> method, int timeout, RpcCallback<T> callback) {
        this.doRpcCall(method, timeout, callback, 0);
    }

    public <T extends TLObject> T doRpcCall(TLMethod<T> method) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, 20000);
    }

    public <T extends TLObject> T doRpcCall(TLMethod<T> method, int timeout) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, timeout, 0);
    }

    public <T extends TLObject> T doRpcCallSide(TLMethod<T> method) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, 20000, this.primaryDc, true);
    }

    public <T extends TLObject> T doRpcCallSide(TLMethod<T> method, int timeout) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, timeout, this.primaryDc, true);
    }

    public <T extends TLObject> T doRpcCallSideGzip(TLMethod<T> method, int timeout) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(new GzipRequest<T>(method), timeout, this.primaryDc, true);
    }

    public <T extends TLObject> T doRpcCallGzip(TLMethod<T> method, int timeout) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(new GzipRequest<T>(method), timeout, 0);
    }

    public <T extends TLObject> T doRpcCallNonAuth(TLMethod<T> method) throws RpcException, java.util.concurrent.TimeoutException {
        return this.doRpcCallNonAuth(method, 20000, this.primaryDc);
    }

    public <T extends TLObject> T doRpcCallNonAuth(TLMethod<T> method, int dcId) throws IOException, java.util.concurrent.TimeoutException {
        return this.doRpcCallNonAuth(method, 20000, dcId);
    }

    public <T extends TLObject> T doRpcCallNonAuth(TLMethod<T> method, int timeout, int dcId) throws RpcException, java.util.concurrent.TimeoutException {
        return this.doRpcCall(method, timeout, dcId, false);
    }

    public <T extends TLObject> void doRpcCallNonAuth(TLMethod<T> method, int timeout, RpcCallback<T> callback) {
        this.doRpcCall(method, timeout, callback, 0, false);
    }

    public boolean doSaveFilePart(long _fileId, int _filePart, byte[] _bytes) throws IOException, java.util.concurrent.TimeoutException {
        TLRequestUploadSaveFilePart tlRequestUploadSaveFilePart = new TLRequestUploadSaveFilePart();
        tlRequestUploadSaveFilePart.setFileId(_fileId);
        tlRequestUploadSaveFilePart.setFilePart(_filePart);
        tlRequestUploadSaveFilePart.setBytes(new TLBytes(_bytes));
        TLBool res = (TLBool)this.doRpcCall(tlRequestUploadSaveFilePart, 45000, this.primaryDc, true);
        return res instanceof TLBoolTrue;
    }

    public boolean doSaveBigFilePart(long _fileId, int _filePart, int _totalParts, byte[] _bytes) throws IOException, java.util.concurrent.TimeoutException {
        TLRequestUploadSaveBigFilePart tlRequestUploadSaveBigFilePart = new TLRequestUploadSaveBigFilePart();
        tlRequestUploadSaveBigFilePart.setFileId(_fileId);
        tlRequestUploadSaveBigFilePart.setFilePart(_filePart);
        tlRequestUploadSaveBigFilePart.setFileTotalParts(_totalParts);
        tlRequestUploadSaveBigFilePart.setBytes(new TLBytes(_bytes));
        TLBool res = (TLBool)this.doRpcCall(tlRequestUploadSaveBigFilePart, 45000, this.primaryDc);
        return res instanceof TLBoolTrue;
    }

    public TLFile doGetFile(int dcId, TLAbsInputFileLocation _location, int _offset, int _limit) throws IOException, java.util.concurrent.TimeoutException {
        TLRequestUploadGetFile tlRequestUploadGetFile = new TLRequestUploadGetFile();
        tlRequestUploadGetFile.setLocation(_location);
        tlRequestUploadGetFile.setOffset(_offset);
        tlRequestUploadGetFile.setLimit(_limit);
        return (TLFile)this.doRpcCall(tlRequestUploadGetFile, 45000, dcId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkDcAuth(int dcId) {
        if (dcId != 0) {
            HashMap<Integer, MTProto> hashMap = this.dcProtos;
            synchronized (hashMap) {
                if (!this.dcProtos.containsKey(dcId)) {
                    TreeMap<Integer, Boolean> treeMap = this.dcRequired;
                    synchronized (treeMap) {
                        this.dcRequired.put(dcId, true);
                        this.dcRequired.notifyAll();
                    }
                }
                if (!this.state.isAuthenticated(dcId)) {
                    TreeMap<Integer, Boolean> treeMap = this.dcRequired;
                    synchronized (treeMap) {
                        this.dcRequired.put(dcId, true);
                        this.dcRequired.notifyAll();
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkDc(int dcId) {
        if (dcId != 0) {
            HashMap<Integer, MTProto> hashMap = this.dcProtos;
            synchronized (hashMap) {
                if (!this.dcProtos.containsKey(dcId)) {
                    TreeMap<Integer, Boolean> treeMap = this.dcRequired;
                    synchronized (treeMap) {
                        if (!this.dcRequired.containsKey(dcId)) {
                            this.dcRequired.put(dcId, false);
                        }
                        this.dcRequired.notifyAll();
                    }
                }
            }
        }
        if (this.mainProto == null) {
            TreeMap<Integer, Boolean> treeMap = this.dcRequired;
            synchronized (treeMap) {
                this.dcRequired.notifyAll();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyCallbacks() {
        HashMap<Integer, RpcCallbackWrapper> hashMap = this.callbacks;
        synchronized (hashMap) {
            this.callbacks.notifyAll();
        }
    }

    private class RpcCallbackWrapper {
        public int id;
        public long requestTime = System.currentTimeMillis();
        public boolean isSent;
        public boolean isCompleted;
        public boolean isConfirmed;
        public RpcCallback callback;
        public long timeoutTime;
        public long timeout;
        public TLMethod method;
        public boolean isAuthRequred;
        public int dcId;

        private RpcCallbackWrapper(int id, TLMethod method, RpcCallback callback) {
            this.id = id;
            this.method = method;
            this.callback = callback;
        }

        public long elapsed() {
            return System.currentTimeMillis() - this.requestTime;
        }
    }

    private class TimeoutThread
    extends Thread {
        public TimeoutThread() {
            this.setName("Timeout#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Serializable key;
            while (!TelegramApi.this.isClosed) {
                RpcCallbackWrapper currentCallback;
                Logger.d(TelegramApi.this.TAG, "Timeout Iteration");
                key = null;
                Object id = null;
                TreeMap treeMap = TelegramApi.this.timeoutTimes;
                synchronized (treeMap) {
                    if (TelegramApi.this.timeoutTimes.isEmpty()) {
                        key = null;
                    } else {
                        try {
                            key = (Long)TelegramApi.this.timeoutTimes.firstKey();
                        }
                        catch (Exception e) {
                            Logger.e(TelegramApi.this.TAG, e);
                        }
                    }
                    if (key == null) {
                        try {
                            TelegramApi.this.timeoutTimes.wait();
                        }
                        catch (InterruptedException e) {
                            // empty catch block
                        }
                        continue;
                    }
                    long delta = (key.longValue() - System.nanoTime()) / 1000000L;
                    if (delta > 0L) {
                        try {
                            TelegramApi.this.timeoutTimes.wait(delta);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                        continue;
                    }
                    id = (Integer)TelegramApi.this.timeoutTimes.remove(key);
                    if (id == null) {
                        continue;
                    }
                }
                Object delta = TelegramApi.this.callbacks;
                synchronized (delta) {
                    currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.remove(id);
                }
                if (currentCallback != null) {
                    delta = currentCallback;
                    synchronized (delta) {
                        if (currentCallback.isCompleted) {
                            Logger.d(TelegramApi.this.TAG, "RPC #" + id + ": Timeout ignored");
                            return;
                        }
                        currentCallback.isCompleted = true;
                    }
                    Logger.d(TelegramApi.this.TAG, "RPC #" + id + ": Timeout (" + currentCallback.elapsed() + " ms)");
                    currentCallback.callback.onError(0, null);
                    continue;
                }
                Logger.d(TelegramApi.this.TAG, "RPC #" + id + ": Timeout ignored2");
            }
            key = TelegramApi.this.timeoutTimes;
            synchronized (key) {
                for (Map.Entry entry : TelegramApi.this.timeoutTimes.entrySet()) {
                    RpcCallbackWrapper currentCallback;
                    Object object = TelegramApi.this.callbacks;
                    synchronized (object) {
                        currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.remove(entry.getValue());
                    }
                    if (currentCallback == null) continue;
                    object = currentCallback;
                    synchronized (object) {
                        if (currentCallback.isCompleted) {
                            return;
                        }
                        currentCallback.isCompleted = true;
                    }
                    Logger.d(TelegramApi.this.TAG, "RPC #" + entry.getValue() + ": Timeout (" + currentCallback.elapsed() + " ms)");
                    currentCallback.callback.onError(0, null);
                }
            }
        }
    }

    private class ConnectionThread
    extends Thread {
        public ConnectionThread() {
            this.setName("Connection#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MTProto waitForDc(final int dcId) throws IOException, java.util.concurrent.TimeoutException {
            Object syncObj;
            Logger.d(TelegramApi.this.TAG, "#" + dcId + ": waitForDc");
            if (TelegramApi.this.isClosed) {
                Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Api is closed");
                throw new TimeoutException();
            }
            Object object = TelegramApi.this.dcSync;
            synchronized (object) {
                syncObj = TelegramApi.this.dcSync.get(dcId);
                if (syncObj == null) {
                    syncObj = new Object();
                    TelegramApi.this.dcSync.put(dcId, syncObj);
                }
            }
            object = syncObj;
            synchronized (object) {
                MTProto proto;
                HashMap hashMap = TelegramApi.this.dcProtos;
                synchronized (hashMap) {
                    proto = (MTProto)TelegramApi.this.dcProtos.get(dcId);
                    if (proto != null && proto.isClosed()) {
                        Logger.d(TelegramApi.this.TAG, "#" + dcId + "proto removed because of death");
                        TelegramApi.this.dcProtos.remove(dcId);
                        proto = null;
                    }
                }
                if (proto == null) {
                    Logger.d(TelegramApi.this.TAG, "#" + dcId + ": Creating proto for dc");
                    ConnectionInfo[] connectionInfo = TelegramApi.this.state.getAvailableConnections(dcId);
                    if (connectionInfo.length == 0) {
                        Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Unable to find proper dc config");
                        TLConfig config = (TLConfig)TelegramApi.this.doRpcCall(new TLRequestHelpGetConfig());
                        TelegramApi.this.state.updateSettings(config);
                        TelegramApi.this.resetConnectionInfo();
                        connectionInfo = TelegramApi.this.state.getAvailableConnections(dcId);
                    }
                    if (connectionInfo.length == 0) {
                        Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Still unable to find proper dc config");
                        throw new TimeoutException();
                    }
                    if (TelegramApi.this.state.getAuthKey(dcId) != null) {
                        byte[] authKey = TelegramApi.this.state.getAuthKey(dcId);
                        if (authKey == null) {
                            throw new TimeoutException();
                        }
                        proto = new MTProto(TelegramApi.this.state.getMtProtoState(dcId), TelegramApi.this.callback, new CallWrapper(){

                            @Override
                            public TLObject wrapObject(TLMethod srcRequest) {
                                return TelegramApi.this.wrapForDc(dcId, srcRequest);
                            }
                        }, 2);
                        TelegramApi.this.dcProtos.put(dcId, proto);
                        return proto;
                    }
                    Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Creating key");
                    Authorizer authorizer = new Authorizer();
                    PqAuth auth = authorizer.doAuth(connectionInfo);
                    if (auth == null) {
                        Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Timed out");
                        throw new TimeoutException();
                    }
                    TelegramApi.this.state.putAuthKey(dcId, auth.getAuthKey());
                    TelegramApi.this.state.setAuthenticated(dcId, false);
                    TelegramApi.this.state.getMtProtoState(dcId).initialServerSalt(auth.getServerSalt());
                    byte[] authKey = TelegramApi.this.state.getAuthKey(dcId);
                    if (authKey == null) {
                        Logger.w(TelegramApi.this.TAG, "#" + dcId + ": auth key == null");
                        throw new TimeoutException();
                    }
                    proto = new MTProto(TelegramApi.this.state.getMtProtoState(dcId), TelegramApi.this.callback, new CallWrapper(){

                        @Override
                        public TLObject wrapObject(TLMethod srcRequest) {
                            return TelegramApi.this.wrapForDc(dcId, srcRequest);
                        }
                    }, 2);
                    TelegramApi.this.dcProtos.put(dcId, proto);
                    return proto;
                }
                Logger.w(TelegramApi.this.TAG, "#" + dcId + ": returning proper proto");
                return proto;
            }
        }

        private MTProto waitForAuthDc(int dcId) throws IOException, java.util.concurrent.TimeoutException {
            Logger.d(TelegramApi.this.TAG, "#" + dcId + ": waitForAuthDc");
            if (TelegramApi.this.isClosed) {
                Logger.w(TelegramApi.this.TAG, "#" + dcId + ": Api is closed");
                throw new TimeoutException();
            }
            MTProto proto = this.waitForDc(dcId);
            if (!TelegramApi.this.state.isAuthenticated(dcId)) {
                Logger.w(TelegramApi.this.TAG, "#" + dcId + ": exporting auth");
                TLRequestAuthExportAuthorization exportAuthorization = new TLRequestAuthExportAuthorization();
                exportAuthorization.setDcId(dcId);
                TLExportedAuthorization exAuth = (TLExportedAuthorization)TelegramApi.this.doRpcCall(exportAuthorization);
                Logger.w(TelegramApi.this.TAG, "#" + dcId + ": importing auth");
                TLRequestAuthImportAuthorization tlRequestAuthImportAuthorization = new TLRequestAuthImportAuthorization();
                tlRequestAuthImportAuthorization.setId(exAuth.getId());
                tlRequestAuthImportAuthorization.setBytes(exAuth.getBytes());
                TelegramApi.this.doRpcCallNonAuth(tlRequestAuthImportAuthorization, 15000, dcId);
                TelegramApi.this.state.setAuthenticated(dcId, true);
            }
            return proto;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            while (!TelegramApi.this.isClosed) {
                Logger.d(TelegramApi.this.TAG, "Connection iteration");
                if (TelegramApi.this.mainProto == null) {
                    long start2;
                    if (TelegramApi.this.state.getAuthKey(TelegramApi.this.primaryDc) == null) {
                        try {
                            start2 = System.currentTimeMillis();
                            this.waitForDc(TelegramApi.this.primaryDc);
                            TelegramApi.this.mainProto = new MTProto(TelegramApi.this.state.getMtProtoState(TelegramApi.this.primaryDc), TelegramApi.this.callback, new CallWrapper(){

                                @Override
                                public TLObject wrapObject(TLMethod srcRequest) {
                                    return TelegramApi.this.wrapForDc(TelegramApi.this.primaryDc, srcRequest);
                                }
                            }, 1);
                            Logger.d(TelegramApi.this.TAG, "#MTProto #" + TelegramApi.this.mainProto.getInstanceIndex() + " created in " + (System.currentTimeMillis() - start2) + " ms");
                        }
                        catch (IOException | java.util.concurrent.TimeoutException e) {
                            Logger.e(TelegramApi.this.TAG, e);
                            try {
                                Thread.sleep(1000L);
                                continue;
                            }
                            catch (InterruptedException e1) {
                                Logger.e(TelegramApi.this.TAG, e1);
                                return;
                            }
                        }
                    } else {
                        start2 = System.currentTimeMillis();
                        TelegramApi.this.mainProto = new MTProto(TelegramApi.this.state.getMtProtoState(TelegramApi.this.primaryDc), TelegramApi.this.callback, new CallWrapper(){

                            @Override
                            public TLObject wrapObject(TLMethod srcRequest) {
                                return TelegramApi.this.wrapForDc(TelegramApi.this.primaryDc, srcRequest);
                            }
                        }, 1);
                        Logger.d(TelegramApi.this.TAG, "#MTProto #" + TelegramApi.this.mainProto.getInstanceIndex() + " created in " + (System.currentTimeMillis() - start2) + " ms");
                    }
                    HashMap start2 = TelegramApi.this.callbacks;
                    synchronized (start2) {
                        TelegramApi.this.callbacks.notifyAll();
                        continue;
                    }
                }
                Integer dcId = null;
                Boolean authRequired = null;
                AbstractMap abstractMap = TelegramApi.this.dcRequired;
                synchronized (abstractMap) {
                    if (TelegramApi.this.dcRequired.isEmpty()) {
                        dcId = null;
                        authRequired = null;
                    } else {
                        try {
                            dcId = (Integer)TelegramApi.this.dcRequired.firstKey();
                        }
                        catch (Exception e) {
                            Logger.e(TelegramApi.this.TAG, e);
                        }
                    }
                    if (dcId == null) {
                        try {
                            TelegramApi.this.dcRequired.wait();
                        }
                        catch (InterruptedException e) {
                            // empty catch block
                        }
                        continue;
                    }
                    authRequired = (Boolean)TelegramApi.this.dcRequired.remove(dcId);
                }
                if (TelegramApi.this.dcProtos.containsKey(dcId)) {
                    if (!authRequired.booleanValue() || TelegramApi.this.state.isAuthenticated(dcId) || !TelegramApi.this.state.isAuthenticated(TelegramApi.this.primaryDc)) continue;
                    try {
                        this.waitForAuthDc(dcId);
                        abstractMap = TelegramApi.this.callbacks;
                        synchronized (abstractMap) {
                            TelegramApi.this.callbacks.notifyAll();
                            continue;
                        }
                    }
                    catch (IOException | java.util.concurrent.TimeoutException e) {
                        try {
                            Thread.sleep(1000L);
                            continue;
                        }
                        catch (InterruptedException e1) {
                            Logger.e(TelegramApi.this.TAG, e1);
                            return;
                        }
                    }
                }
                try {
                    if (authRequired.booleanValue() && !TelegramApi.this.state.isAuthenticated(dcId) && TelegramApi.this.state.isAuthenticated(TelegramApi.this.primaryDc)) {
                        this.waitForAuthDc(dcId);
                    } else {
                        this.waitForDc(dcId);
                    }
                    HashMap e = TelegramApi.this.callbacks;
                    synchronized (e) {
                        TelegramApi.this.callbacks.notifyAll();
                    }
                }
                catch (IOException | java.util.concurrent.TimeoutException e) {
                    Logger.e(TelegramApi.this.TAG, e);
                }
            }
        }

    }

    private class SenderThread
    extends Thread {
        public SenderThread() {
            this.setName("Sender#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            while (!TelegramApi.this.isClosed) {
                Logger.d(TelegramApi.this.TAG, "Sender iteration");
                RpcCallbackWrapper wrapper = null;
                HashMap hashMap = TelegramApi.this.callbacks;
                synchronized (hashMap) {
                    for (RpcCallbackWrapper w : TelegramApi.this.callbacks.values()) {
                        if (w.isSent) continue;
                        if (w.dcId == 0 && TelegramApi.this.mainProto != null && (TelegramApi.this.state.isAuthenticated(TelegramApi.this.primaryDc) || !w.isAuthRequred)) {
                            wrapper = w;
                            break;
                        }
                        if (w.dcId == 0 || !TelegramApi.this.dcProtos.containsKey(w.dcId) || !TelegramApi.this.state.isAuthenticated(w.dcId) && w.isAuthRequred) continue;
                        wrapper = w;
                        break;
                    }
                    if (wrapper == null) {
                        try {
                            TelegramApi.this.callbacks.wait();
                        }
                        catch (InterruptedException e) {
                            Logger.e(TelegramApi.this.TAG, e);
                            return;
                        }
                    }
                }
                if (TelegramApi.this.mainProto == null) continue;
                if (wrapper.dcId == 0) {
                    if (!TelegramApi.this.state.isAuthenticated(TelegramApi.this.primaryDc) && wrapper.isAuthRequred) continue;
                    hashMap = TelegramApi.this.callbacks;
                    synchronized (hashMap) {
                        boolean isHighPriority = wrapper.callback != null && wrapper.callback instanceof RpcCallbackEx;
                        int rpcId = TelegramApi.this.mainProto.sendRpcMessage(wrapper.method, wrapper.timeout, isHighPriority);
                        TelegramApi.this.sentRequests.put(rpcId, wrapper.id);
                        wrapper.isSent = true;
                        Logger.d(TelegramApi.this.TAG, "#> #" + wrapper.id + " sent to MTProto #" + TelegramApi.this.mainProto.getInstanceIndex() + " with id #" + rpcId);
                        continue;
                    }
                }
                if (!TelegramApi.this.dcProtos.containsKey(wrapper.dcId) || !TelegramApi.this.state.isAuthenticated(wrapper.dcId) && wrapper.isAuthRequred) continue;
                MTProto proto = (MTProto)TelegramApi.this.dcProtos.get(wrapper.dcId);
                HashMap isHighPriority = TelegramApi.this.callbacks;
                synchronized (isHighPriority) {
                    boolean isHighPriority2 = wrapper.callback != null && wrapper.callback instanceof RpcCallbackEx;
                    int rpcId = proto.sendRpcMessage(wrapper.method, wrapper.timeout, isHighPriority2);
                    TelegramApi.this.sentRequests.put(rpcId, wrapper.id);
                    wrapper.isSent = true;
                    Logger.d(TelegramApi.this.TAG, "#> #" + wrapper.id + " sent to MTProto #" + proto.getInstanceIndex() + " with id #" + rpcId);
                }
            }
        }
    }

    private class ProtoCallback
    implements MTProtoCallback {
        private ProtoCallback() {
        }

        @Override
        public void onSessionCreated(MTProto proto) {
            if (TelegramApi.this.isClosed) {
                return;
            }
            Logger.w(TelegramApi.this.TAG, proto + ": onSessionCreated");
            if (proto == TelegramApi.this.mainProto) {
                TelegramApi.this.registeredInApi.add(TelegramApi.this.primaryDc);
            } else {
                for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                    if (p.getValue() != proto) continue;
                    TelegramApi.this.registeredInApi.add(p.getKey());
                    break;
                }
            }
            TelegramApi.this.apiCallback.onUpdatesInvalidated(TelegramApi.this);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onAuthInvalidated(MTProto proto) {
            if (TelegramApi.this.isClosed) {
                return;
            }
            if (proto == TelegramApi.this.mainProto) {
                AbstractMap abstractMap = TelegramApi.this.dcRequired;
                synchronized (abstractMap) {
                    TelegramApi.this.mainProto.close();
                    TelegramApi.this.mainProto = null;
                    TelegramApi.this.state.setAuthenticated(TelegramApi.this.primaryDc, false);
                    TelegramApi.this.dcRequired.notifyAll();
                }
                abstractMap = TelegramApi.this.dcProtos;
                synchronized (abstractMap) {
                    for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                        ((MTProto)p.getValue()).close();
                        TelegramApi.this.state.setAuthenticated((Integer)p.getKey(), false);
                    }
                }
                TelegramApi.this.apiCallback.onAuthCancelled(TelegramApi.this);
            } else {
                AbstractMap abstractMap = TelegramApi.this.dcProtos;
                synchronized (abstractMap) {
                    for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                        if (p.getValue() != proto) continue;
                        TelegramApi.this.state.setAuthenticated((Integer)p.getKey(), false);
                        TelegramApi.this.dcProtos.remove(p.getKey());
                        break;
                    }
                }
                abstractMap = TelegramApi.this.dcRequired;
                synchronized (abstractMap) {
                    TelegramApi.this.dcRequired.notifyAll();
                }
            }
        }

        @Override
        public void onApiMessage(byte[] message, MTProto proto) {
            if (TelegramApi.this.isClosed) {
                return;
            }
            if (proto == TelegramApi.this.mainProto) {
                TelegramApi.this.registeredInApi.add(TelegramApi.this.primaryDc);
            } else {
                for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                    if (p.getValue() != proto) continue;
                    TelegramApi.this.registeredInApi.add(p.getKey());
                    break;
                }
            }
            try {
                TLObject object = TelegramApi.this.apiContext.deserializeMessage(message);
                TelegramApi.this.onMessageArrived(object);
            }
            catch (Throwable t) {
                Logger.e(TelegramApi.this.TAG, t);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onRpcResult(int callId, byte[] response, MTProto proto) {
            block18 : {
                if (TelegramApi.this.isClosed) {
                    return;
                }
                if (proto == TelegramApi.this.mainProto) {
                    TelegramApi.this.registeredInApi.add(TelegramApi.this.primaryDc);
                } else {
                    for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                        if (p.getValue() != proto) continue;
                        TelegramApi.this.registeredInApi.add(p.getKey());
                        break;
                    }
                }
                try {
                    Map.Entry p;
                    RpcCallbackWrapper currentCallback = null;
                    p = TelegramApi.this.callbacks;
                    synchronized (p) {
                        if (TelegramApi.this.sentRequests.containsKey(callId)) {
                            currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.remove(TelegramApi.this.sentRequests.remove(callId));
                        }
                    }
                    if (currentCallback == null || currentCallback.method == null) break block18;
                    long start = System.currentTimeMillis();
                    Object object = currentCallback.method.deserializeResponse(response, (TLContext)TelegramApi.this.apiContext);
                    Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " deserialized " + object + " in " + (System.currentTimeMillis() - start) + " ms");
                    Object object2 = currentCallback;
                    synchronized (object2) {
                        if (currentCallback.isCompleted) {
                            Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " ignored " + object + " in " + currentCallback.elapsed() + " ms");
                            return;
                        }
                        currentCallback.isCompleted = true;
                    }
                    Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " " + object + " in " + currentCallback.elapsed() + " ms");
                    object2 = TelegramApi.this.timeoutTimes;
                    synchronized (object2) {
                        TelegramApi.this.timeoutTimes.remove(currentCallback.timeoutTime);
                    }
                    if (currentCallback.callback != null) {
                        currentCallback.callback.onResult(object);
                    }
                }
                catch (Throwable t) {
                    Logger.e(TelegramApi.this.TAG, t);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onRpcError(int callId, int errorCode, String message, MTProto proto) {
            block27 : {
                if (TelegramApi.this.isClosed) {
                    return;
                }
                if (errorCode == 400 && message != null && (message.startsWith("CONNECTION_NOT_INITED") || message.startsWith("CONNECTION_LAYER_INVALID"))) {
                    Map.Entry p2;
                    Logger.w(TelegramApi.this.TAG, proto + ": (!)Error #400 " + message);
                    int dc = -1;
                    if (proto == TelegramApi.this.mainProto) {
                        dc = TelegramApi.this.primaryDc;
                    } else {
                        for (Map.Entry p2 : TelegramApi.this.dcProtos.entrySet()) {
                            if (p2.getValue() != proto) continue;
                            dc = (Integer)p2.getKey();
                            break;
                        }
                    }
                    if (dc < 0) {
                        return;
                    }
                    TelegramApi.this.registeredInApi.remove(dc);
                    p2 = TelegramApi.this.callbacks;
                    synchronized (p2) {
                        RpcCallbackWrapper currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.remove(TelegramApi.this.sentRequests.remove(callId));
                        if (currentCallback != null) {
                            currentCallback.isSent = false;
                            TelegramApi.this.callbacks.notifyAll();
                        }
                    }
                    return;
                }
                if (proto == TelegramApi.this.mainProto) {
                    TelegramApi.this.registeredInApi.add(TelegramApi.this.primaryDc);
                } else {
                    for (Map.Entry p : TelegramApi.this.dcProtos.entrySet()) {
                        if (p.getValue() != proto) continue;
                        TelegramApi.this.registeredInApi.add(p.getKey());
                        break;
                    }
                }
                try {
                    Map.Entry p;
                    RpcCallbackWrapper currentCallback = null;
                    p = TelegramApi.this.callbacks;
                    synchronized (p) {
                        if (TelegramApi.this.sentRequests.containsKey(callId)) {
                            currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.remove(TelegramApi.this.sentRequests.remove(callId));
                        }
                    }
                    if (currentCallback == null) break block27;
                    p = currentCallback;
                    synchronized (p) {
                        if (currentCallback.isCompleted) {
                            Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " ignored error #" + errorCode + " " + message + " in " + currentCallback.elapsed() + " ms");
                            return;
                        }
                        currentCallback.isCompleted = true;
                    }
                    Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " error #" + errorCode + " " + message + " in " + currentCallback.elapsed() + " ms");
                    p = TelegramApi.this.timeoutTimes;
                    synchronized (p) {
                        TelegramApi.this.timeoutTimes.remove(currentCallback.timeoutTime);
                    }
                    if (currentCallback.callback != null) {
                        currentCallback.callback.onError(errorCode, message);
                    }
                }
                catch (Throwable t) {
                    Logger.e(TelegramApi.this.TAG, t);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onConfirmed(int callId) {
            RpcCallbackWrapper currentCallback = null;
            Object object = TelegramApi.this.callbacks;
            synchronized (object) {
                if (TelegramApi.this.sentRequests.containsKey(callId)) {
                    currentCallback = (RpcCallbackWrapper)TelegramApi.this.callbacks.get(TelegramApi.this.sentRequests.get(callId));
                }
            }
            if (currentCallback != null) {
                Logger.d(TelegramApi.this.TAG, "<< #" + currentCallback.id + " confirmed in " + currentCallback.elapsed() + " ms");
                object = currentCallback;
                synchronized (object) {
                    if (currentCallback.isCompleted || currentCallback.isConfirmed) {
                        return;
                    }
                    currentCallback.isConfirmed = true;
                }
                if (currentCallback.callback instanceof RpcCallbackEx) {
                    ((RpcCallbackEx)currentCallback.callback).onConfirmed();
                }
            }
        }
    }

}

