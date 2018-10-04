
package org.telegram.mtproto.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.mtproto.CallWrapper;
import org.telegram.mtproto.MTProto;
import org.telegram.mtproto.log.Logger;
import org.telegram.mtproto.schedule.PrepareSchedule;
import org.telegram.mtproto.schedule.PreparedPackage;
import org.telegram.mtproto.time.TimeOverlord;
import org.telegram.mtproto.tl.MTInvokeAfter;
import org.telegram.mtproto.tl.MTMessage;
import org.telegram.mtproto.tl.MTMessagesContainer;
import org.telegram.mtproto.tl.MTMsgsAck;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;

public class Scheduller {
    private static final AtomicInteger messagesIds = new AtomicInteger(1);
    private static final ConcurrentHashMap<Long, Long> idGenerationTime = new ConcurrentHashMap();
    private static final int SCHEDULLER_TIMEOUT = 15000;
    private static final long CONFIRM_TIMEOUT = 60000L;
    private static final int MAX_WORKLOAD_SIZE = 3072;
    private static final int BIG_MESSAGE_SIZE = 1024;
    private static final long RETRY_TIMEOUT = 5000L;
    private static final int MAX_ACK_COUNT = 5;
    private static final int PRIORITY_HIGH = 1;
    private static final int PRIORITY_NORMAL = 0;
    private static final int STATE_QUEUED = 0;
    private static final int STATE_SENT = 1;
    private static final int STATE_CONFIRMED = 2;
    private final String TAG;
    private SortedMap<Integer, SchedullerPackage> messages = Collections.synchronizedSortedMap(new TreeMap());
    private HashSet<Long> currentMessageGeneration = new HashSet();
    private HashSet<Long> confirmedMessages = new HashSet();
    private long firstConfirmTime;
    private long lastMessageId;
    private long lastDependId;
    private int seqNo;
    private CallWrapper wrapper;

    public Scheduller(MTProto mtProto, CallWrapper wrapper) {
        this.TAG = "MTProto#" + mtProto.getInstanceIndex() + "#Scheduller";
        this.wrapper = wrapper;
    }

    public synchronized void updateMessageId(long newLastMessageId) {
        if (newLastMessageId > this.lastMessageId) {
            this.lastMessageId = newLastMessageId;
        }
    }

    private synchronized long generateMessageId() {
        long messageId = TimeOverlord.getInstance().createWeakMessageId();
        if (messageId <= this.lastMessageId) {
            messageId = this.lastMessageId += 4L;
        }
        while (idGenerationTime.containsKey(messageId)) {
            messageId += 4L;
        }
        this.lastMessageId = messageId;
        idGenerationTime.put(messageId, this.getCurrentTime());
        this.currentMessageGeneration.add(messageId);
        return messageId;
    }

    private synchronized int generateSeqNoWeak() {
        return this.seqNo * 2;
    }

    private synchronized int generateSeqNo() {
        int res = this.seqNo * 2 + 1;
        ++this.seqNo;
        return res;
    }

    private synchronized void generateParams(SchedullerPackage schedullerPackage) {
        schedullerPackage.messageId = this.generateMessageId();
        schedullerPackage.seqNo = this.generateSeqNo();
        schedullerPackage.idGenerationTime = this.getCurrentTime();
        schedullerPackage.relatedMessageIds.add(schedullerPackage.messageId);
        schedullerPackage.generatedMessageIds.add(schedullerPackage.messageId);
    }

    private long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getMessageIdGenerationTime(long msgId) {
        if (idGenerationTime.containsKey(msgId)) {
            return idGenerationTime.get(msgId);
        }
        return 0L;
    }

    public int postMessageDelayed(TLObject object, boolean isRpc, long timeout, int delay, int contextId, boolean highPrioroty) {
        int id = messagesIds.incrementAndGet();
        SchedullerPackage schedullerPackage = new SchedullerPackage(id);
        schedullerPackage.object = object;
        schedullerPackage.addTime = this.getCurrentTime();
        schedullerPackage.scheduleTime = schedullerPackage.addTime + (long)delay;
        schedullerPackage.expiresTime = schedullerPackage.scheduleTime + timeout;
        schedullerPackage.ttlTime = schedullerPackage.scheduleTime + timeout * 2L;
        schedullerPackage.isRpc = isRpc;
        schedullerPackage.queuedToChannel = contextId;
        schedullerPackage.priority = highPrioroty ? 1 : 0;
        schedullerPackage.isDepend = highPrioroty;
        schedullerPackage.supportTag = object.toString();
        schedullerPackage.serverErrorCount = 0;
        this.messages.put(id, schedullerPackage);
        return id;
    }

    public int postMessage(TLObject object, boolean isApi, long timeout) {
        return this.postMessageDelayed(object, isApi, timeout, 0, -1, false);
    }

    public int postMessage(TLObject object, boolean isApi, long timeout, boolean highPrioroty) {
        return this.postMessageDelayed(object, isApi, timeout, 0, -1, highPrioroty);
    }

    public synchronized void prepareScheduller(PrepareSchedule prepareSchedule, int[] connectionIds) {
        long time = this.getCurrentTime();
        block0 : for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.queuedToChannel == -1) continue;
            for (int id : connectionIds) {
                if (schedullerPackage.queuedToChannel == id) continue block0;
            }
            this.forgetMessage(schedullerPackage.id);
        }
        if (connectionIds.length == 0) {
            prepareSchedule.setDelay(15000L);
            prepareSchedule.setAllowedContexts(connectionIds);
            prepareSchedule.setDoWait(true);
            return;
        }
        long minDelay = 15000L;
        boolean allConnections = false;
        boolean doWait = true;
        HashSet<Integer> supportedConnections = new HashSet<Integer>();
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            boolean isPendingPackage = false;
            long packageTime = 0L;
            if (schedullerPackage.state == 0) {
                isPendingPackage = true;
                packageTime = schedullerPackage.scheduleTime <= time ? 0L : Math.max(schedullerPackage.scheduleTime - time, 0L);
            } else if (schedullerPackage.state == 1 && this.getCurrentTime() <= schedullerPackage.expiresTime && time - schedullerPackage.lastAttemptTime >= 5000L) {
                isPendingPackage = true;
                packageTime = 0L;
            }
            if (!isPendingPackage) continue;
            if (schedullerPackage.queuedToChannel == -1) {
                allConnections = true;
            } else {
                supportedConnections.add(schedullerPackage.queuedToChannel);
            }
            if (packageTime == 0L) {
                minDelay = 0L;
                doWait = false;
                continue;
            }
            minDelay = Math.min(packageTime, minDelay);
        }
        prepareSchedule.setDoWait(doWait);
        prepareSchedule.setDelay(minDelay);
        if (allConnections) {
            prepareSchedule.setAllowedContexts(connectionIds);
        } else {
            Integer[] allowedBoxed = supportedConnections.toArray(new Integer[0]);
            int[] allowed = new int[allowedBoxed.length];
            for (int i = 0; i < allowed.length; ++i) {
                allowed[i] = allowedBoxed[i];
            }
            prepareSchedule.setAllowedContexts(allowed);
        }
    }

    public synchronized void registerFastConfirm(long msgId, int fastConfirm) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            boolean contains = false;
            for (Long relatedMsgId : schedullerPackage.relatedMessageIds) {
                if (relatedMsgId != msgId) continue;
                contains = true;
                break;
            }
            if (!contains) continue;
            schedullerPackage.relatedFastConfirm.add(fastConfirm);
        }
    }

    public int mapSchedullerId(long msgId) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (!schedullerPackage.generatedMessageIds.contains(msgId)) continue;
            return schedullerPackage.id;
        }
        return 0;
    }

    public void resetMessageId() {
        this.lastMessageId = 0L;
        this.lastDependId = 0L;
    }

    public void resetSession() {
        this.lastMessageId = 0L;
        this.lastDependId = 0L;
        this.seqNo = 0;
        this.currentMessageGeneration.clear();
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            schedullerPackage.idGenerationTime = 0L;
            schedullerPackage.dependMessageId = 0L;
            schedullerPackage.messageId = 0L;
            schedullerPackage.seqNo = 0;
        }
    }

    public boolean isMessageFromCurrentGeneration(long msgId) {
        return this.currentMessageGeneration.contains(msgId);
    }

    public void resendAsNewMessage(long msgId) {
        this.resendAsNewMessageDelayed(msgId, 0);
    }

    public void resendAsNewMessageDelayed(long msgId, int delay) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (!schedullerPackage.relatedMessageIds.contains(msgId)) continue;
            schedullerPackage.idGenerationTime = 0L;
            schedullerPackage.dependMessageId = 0L;
            schedullerPackage.messageId = 0L;
            schedullerPackage.seqNo = 0;
            schedullerPackage.state = 0;
            schedullerPackage.scheduleTime = this.getCurrentTime() + (long)delay;
            Logger.d(this.TAG, "Resending as new #" + schedullerPackage.id);
        }
    }

    public void resendMessage(long msgId) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (!schedullerPackage.relatedMessageIds.contains(msgId)) continue;
            schedullerPackage.state = 0;
            schedullerPackage.lastAttemptTime = 0L;
        }
    }

    public int[] mapFastConfirm(int fastConfirm) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.state != 1 || !schedullerPackage.relatedFastConfirm.contains(fastConfirm)) continue;
            res.add(schedullerPackage.id);
        }
        int[] res2 = new int[res.size()];
        for (int i = 0; i < res2.length; ++i) {
            res2[i] = (Integer)res.get(i);
        }
        return res2;
    }

    public void onMessageFastConfirmed(int fastConfirm) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.state != 1 || !schedullerPackage.relatedFastConfirm.contains(fastConfirm)) continue;
            schedullerPackage.state = 2;
        }
    }

    public void onMessageConfirmed(long msgId) {
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.state != 1 || !schedullerPackage.relatedMessageIds.contains(msgId)) continue;
            schedullerPackage.state = 2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void confirmMessage(long msgId) {
        HashSet<Long> hashSet = this.confirmedMessages;
        synchronized (hashSet) {
            this.confirmedMessages.add(msgId);
            if (this.firstConfirmTime == 0L) {
                this.firstConfirmTime = this.getCurrentTime();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isMessaveConfirmed(long msgId) {
        HashSet<Long> hashSet = this.confirmedMessages;
        synchronized (hashSet) {
            return this.confirmedMessages.contains(msgId);
        }
    }

    public synchronized void forgetMessageByMsgId(long msgId) {
        int scId = this.mapSchedullerId(msgId);
        if (scId > 0) {
            this.forgetMessage(scId);
        }
    }

    public synchronized void forgetMessage(int id) {
        Logger.d(this.TAG, "Forgetting message: #" + id);
        this.messages.remove(id);
    }

    private synchronized ArrayList<SchedullerPackage> actualPackages(int contextId) {
        ArrayList<SchedullerPackage> foundedPackages = new ArrayList<SchedullerPackage>();
        long time = this.getCurrentTime();
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.queuedToChannel != -1 && contextId != schedullerPackage.queuedToChannel) continue;
            boolean isPendingPackage = false;
            if (schedullerPackage.ttlTime <= this.getCurrentTime()) {
                this.forgetMessage(schedullerPackage.id);
                continue;
            }
            if (schedullerPackage.state == 0) {
                if (schedullerPackage.scheduleTime <= time) {
                    isPendingPackage = true;
                }
            } else if (schedullerPackage.state == 1 && this.getCurrentTime() <= schedullerPackage.expiresTime && this.getCurrentTime() - schedullerPackage.lastAttemptTime >= 5000L) {
                isPendingPackage = true;
            }
            if (!isPendingPackage) continue;
            if (schedullerPackage.serialized == null) {
                try {
                    schedullerPackage.serialized = schedullerPackage.isRpc ? this.wrapper.wrapObject((TLMethod)schedullerPackage.object).serialize() : schedullerPackage.object.serialize();
                }
                catch (IOException e) {
                    Logger.e(this.TAG, e);
                    this.forgetMessage(schedullerPackage.id);
                    continue;
                }
            }
            foundedPackages.add(schedullerPackage);
        }
        return foundedPackages;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized PreparedPackage doSchedule(int contextId, boolean isInited) {
        ArrayList<SchedullerPackage> foundedPackages;
        ArrayList<SchedullerPackage> packages;
        boolean useHighPriority;
        int totalSize;
        foundedPackages = this.actualPackages(contextId);
        HashSet<Long> hashSet = this.confirmedMessages;
        synchronized (hashSet) {
            if (foundedPackages.size() == 0 && (this.confirmedMessages.size() <= 5 || System.nanoTime() - this.firstConfirmTime < 60000L)) {
                return null;
            }
        }
        useHighPriority = false;
        for (SchedullerPackage p : foundedPackages) {
            if (p.priority != 1) continue;
            useHighPriority = true;
            break;
        }
        packages = new ArrayList<SchedullerPackage>();
        if (useHighPriority) {
            Logger.d("Scheduller", "Using high priority scheduling");
            totalSize = 0;
            for (SchedullerPackage p : foundedPackages) {
                if (p.priority != 1) continue;
                packages.add(p);
                if ((totalSize += p.serialized.length) <= 3072) continue;
                break;
            }
        } else {
            totalSize = 0;
            for (SchedullerPackage p : foundedPackages) {
                packages.add(p);
                Logger.d("Scheduller", "Prepare package: " + p.supportTag + " of size " + p.serialized.length);
                Logger.d("Scheduller", "Total size: " + (totalSize += p.serialized.length));
                if (totalSize <= 3072) continue;
                break;
            }
        }
        Logger.d(this.TAG, "Iteration: count: " + packages.size() + ", confirm:" + this.confirmedMessages.size());
        Logger.d(this.TAG, "Building package");
        if (foundedPackages.size() == 0 && this.confirmedMessages.size() != 0) {
            Long[] msgIds;
            HashSet<Long> hashSet = this.confirmedMessages;
            synchronized (hashSet) {
                msgIds = this.confirmedMessages.toArray(new Long[this.confirmedMessages.size()]);
                this.confirmedMessages.clear();
            }
            MTMsgsAck ack = new MTMsgsAck(msgIds);
            Logger.d(this.TAG, "Single msg_ack");
            try {
                return new PreparedPackage(this.generateSeqNoWeak(), this.generateMessageId(), ack.serialize(), useHighPriority);
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
                return null;
            }
        }
        if (foundedPackages.size() == 1 && this.confirmedMessages.size() == 0) {
            SchedullerPackage schedullerPackage = foundedPackages.get(0);
            schedullerPackage.state = 1;
            if (schedullerPackage.idGenerationTime == 0L) {
                this.generateParams(schedullerPackage);
            }
            Logger.d(this.TAG, "Single package: #" + schedullerPackage.id + " " + schedullerPackage.supportTag + " (" + schedullerPackage.messageId + ", " + schedullerPackage.seqNo + ")");
            schedullerPackage.writtenToChannel = contextId;
            schedullerPackage.lastAttemptTime = this.getCurrentTime();
            return new PreparedPackage(schedullerPackage.seqNo, schedullerPackage.messageId, schedullerPackage.serialized, useHighPriority);
        }
        MTMessagesContainer container = new MTMessagesContainer();
        if (this.confirmedMessages.size() > 0 && !useHighPriority || !isInited) {
            try {
                Long[] msgIds;
                HashSet<Long> e = this.confirmedMessages;
                synchronized (e) {
                    msgIds = this.confirmedMessages.toArray(new Long[0]);
                    this.confirmedMessages.clear();
                }
                MTMsgsAck ack = new MTMsgsAck(msgIds);
                Logger.d(this.TAG, "Adding msg_ack: " + msgIds.length);
                container.getMessages().add(new MTMessage(this.generateMessageId(), this.generateSeqNoWeak(), ack.serialize()));
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
        }
        for (SchedullerPackage schedullerPackage : packages) {
            schedullerPackage.state = 1;
            if (schedullerPackage.idGenerationTime == 0L) {
                this.generateParams(schedullerPackage);
            }
            if (schedullerPackage.isDepend) {
                if (schedullerPackage.dependMessageId == 0L) {
                    schedullerPackage.dependMessageId = this.lastDependId > 0L ? this.lastDependId : -1L;
                }
                this.lastDependId = schedullerPackage.messageId;
            }
            schedullerPackage.writtenToChannel = contextId;
            schedullerPackage.lastAttemptTime = this.getCurrentTime();
            if (schedullerPackage.isDepend && schedullerPackage.dependMessageId > 0L) {
                Logger.d(this.TAG, "Adding package: #" + schedullerPackage.id + " " + schedullerPackage.supportTag + " (" + schedullerPackage.messageId + " on " + schedullerPackage.dependMessageId + ", " + schedullerPackage.seqNo + ")");
                MTInvokeAfter invokeAfter = new MTInvokeAfter(schedullerPackage.dependMessageId, schedullerPackage.serialized);
                try {
                    container.getMessages().add(new MTMessage(schedullerPackage.messageId, schedullerPackage.seqNo, invokeAfter.serialize()));
                }
                catch (IOException e) {
                    Logger.e(this.TAG, e);
                }
                continue;
            }
            Logger.d(this.TAG, "Adding package: #" + schedullerPackage.id + " " + schedullerPackage.supportTag + " (" + schedullerPackage.messageId + ", " + schedullerPackage.seqNo + ")");
            container.getMessages().add(new MTMessage(schedullerPackage.messageId, schedullerPackage.seqNo, schedullerPackage.serialized));
        }
        long containerMessageId = this.generateMessageId();
        int containerSeq = this.generateSeqNoWeak();
        for (SchedullerPackage schedullerPackage : packages) {
            schedullerPackage.relatedMessageIds.add(containerMessageId);
        }
        Logger.d(this.TAG, "Sending Package (" + containerMessageId + ", " + containerSeq + ")");
        try {
            return new PreparedPackage(containerSeq, containerMessageId, container.serialize(), useHighPriority);
        }
        catch (IOException e) {
            Logger.e(this.TAG, e);
            return null;
        }
    }

    public synchronized void onServerError(long msgId) {
    }

    public synchronized void onConnectionDies(int connectionId) {
        Logger.d(this.TAG, "Connection dies " + connectionId);
        for (SchedullerPackage schedullerPackage : this.messages.values().toArray(new SchedullerPackage[0])) {
            if (schedullerPackage.writtenToChannel != connectionId) continue;
            if (schedullerPackage.queuedToChannel != -1) {
                Logger.d(this.TAG, "Removing: #" + schedullerPackage.id + " " + schedullerPackage.supportTag);
                this.forgetMessage(schedullerPackage.id);
                continue;
            }
            if (schedullerPackage.isRpc) {
                if (schedullerPackage.state != 2 && schedullerPackage.state != 0) continue;
                Logger.d(this.TAG, "Re-schedule: #" + schedullerPackage.id + " " + schedullerPackage.supportTag);
                schedullerPackage.state = 0;
                schedullerPackage.lastAttemptTime = 0L;
                continue;
            }
            if (schedullerPackage.state != 1) continue;
            Logger.d(this.TAG, "Re-schedule: #" + schedullerPackage.id + " " + schedullerPackage.supportTag);
            schedullerPackage.state = 0;
            schedullerPackage.lastAttemptTime = 0L;
        }
    }

    private class SchedullerPackage {
        public String supportTag;
        public int id;
        public TLObject object;
        public byte[] serialized;
        public long addTime;
        public long scheduleTime;
        public long expiresTime;
        public long ttlTime;
        public long lastAttemptTime;
        public int writtenToChannel = -1;
        public int queuedToChannel = -1;
        public int state;
        public int priority;
        public boolean isDepend;
        public boolean isSent;
        public long idGenerationTime;
        public long dependMessageId;
        public long messageId;
        public int seqNo;
        public HashSet<Integer> relatedFastConfirm = new HashSet();
        public HashSet<Long> relatedMessageIds = new HashSet();
        public HashSet<Long> generatedMessageIds = new HashSet();
        public int serverErrorCount;
        public boolean isRpc;

        public SchedullerPackage(int id) {
            this.id = id;
        }
    }

}

