
package org.telegram.api.engine.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.api.engine.Logger;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.engine.file.UploadListener;
import org.telegram.mtproto.secure.CryptoUtils;
import org.telegram.mtproto.secure.Entropy;

public class Uploader {
    public static final int FILE_QUEUED = 0;
    public static final int FILE_IN_PROGRESS = 1;
    public static final int FILE_COMPLETED = 2;
    public static final int FILE_CANCELED = 3;
    private static final int KB = 1024;
    private static final int MB = 1048576;
    private static final int BLOCK_QUEUED = 0;
    private static final int BLOCK_DOWNLOADING = 1;
    private static final int BLOCK_COMPLETED = 2;
    private static final int PARALLEL_DOWNLOAD_COUNT = 2;
    private static final int PARALLEL_PARTS_COUNT = 4;
    private static final int[] BLOCK_SIZES = new int[]{262144, 524288};
    private static final long DEFAULT_DELAY = 15000L;
    private static final int BIG_FILE_MIN = 10485760;
    private static final int MAX_BLOCK_COUNT = 3000;
    private final AtomicInteger fileIds = new AtomicInteger(1);
    private final String TAG;
    private final Object threadLocker = new Object();
    private TelegramApi api;
    private ArrayList<UploadTask> tasks = new ArrayList();
    private ArrayList<UploadFileThread> threads = new ArrayList();
    private Random rnd = new Random();

    public Uploader(TelegramApi api) {
        this.TAG = api.toString() + "#Uploader";
        this.api = api;
        for (int i = 0; i < 4; ++i) {
            UploadFileThread thread = new UploadFileThread();
            thread.start();
            this.threads.add(thread);
        }
    }

    public TelegramApi getApi() {
        return this.api;
    }

    private synchronized UploadTask getTask(int taskId) {
        for (UploadTask task : this.tasks) {
            if (task.taskId != taskId) continue;
            return task;
        }
        return null;
    }

    public synchronized void cancelTask(int taskId) {
        UploadTask task = this.getTask(taskId);
        if (task != null && task.state != 2) {
            task.state = 3;
            Logger.d(this.TAG, "File #" + task.taskId + "| Canceled");
        }
        this.updateFileQueueStates();
    }

    public synchronized int getTaskState(int taskId) {
        UploadTask task = this.getTask(taskId);
        if (task != null) {
            return task.state;
        }
        return 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void waitForTask(int taskId) {
        int state;
        while ((state = this.getTaskState(taskId)) != 2 && state != 3) {
            Object object = this.threadLocker;
            synchronized (object) {
                try {
                    this.threadLocker.wait(15000L);
                }
                catch (InterruptedException e) {
                    Logger.e(this.TAG, e);
                    return;
                }
            }
        }
        return;
    }

    public UploadResult getUploadResult(int taskId) {
        UploadTask task = this.getTask(taskId);
        if (task == null) {
            return null;
        }
        if (task.state != 2) {
            return null;
        }
        return new UploadResult(task.uniqId, task.blocks.length, task.hash, task.usedBigFile);
    }

    public synchronized int requestTask(String srcFile, UploadListener listener) {
        int totalBlockCount;
        UploadTask task = new UploadTask();
        task.taskId = this.fileIds.getAndIncrement();
        task.uniqId = Entropy.getInstance().generateRandomId();
        task.listener = listener;
        task.srcFile = srcFile;
        try {
            File testFile = new File(srcFile);
            if (testFile.exists()) {
                Logger.d(this.TAG, "File exists");
            } else {
                Logger.d(this.TAG, "File doesn't exists");
            }
            task.file = new RandomAccessFile(new File(srcFile), "r");
            task.size = (int)task.file.length();
            if (task.size >= 10485760) {
                task.usedBigFile = true;
                Logger.d(this.TAG, "File #" + task.uniqId + "| Using big file method");
            } else {
                task.usedBigFile = false;
            }
            long start = System.currentTimeMillis();
            Logger.d(this.TAG, "File #" + task.uniqId + "| Calculating hash");
            task.hash = CryptoUtils.MD5(task.file);
            Logger.d(this.TAG, "File #" + task.uniqId + "| Hash " + task.hash + " in " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (FileNotFoundException e) {
            Logger.e(this.TAG, e);
        }
        catch (IOException e) {
            Logger.e(this.TAG, e);
        }
        task.blockSize = BLOCK_SIZES[BLOCK_SIZES.length - 1];
        for (int size : BLOCK_SIZES) {
            int totalBlockCount2 = (int)Math.ceil((double)task.size / (double)size);
            if (totalBlockCount2 >= 3000) continue;
            task.blockSize = size;
            break;
        }
        Logger.d(this.TAG, "File #" + task.uniqId + "| Using block size: " + task.blockSize);
        task.blockCount = totalBlockCount = (int)Math.ceil((double)task.size / (double)task.blockSize);
        Logger.w(this.TAG, "Number of blocks " + totalBlockCount);
        task.blocks = new UploadBlock[totalBlockCount];
        int i = 0;
        while (i < totalBlockCount) {
            task.blocks[i] = new UploadBlock();
            task.blocks[i].task = task;
            task.blocks[i].index = i++;
            task.blocks[i].state = 0;
        }
        task.state = 0;
        task.queueTime = System.nanoTime();
        this.tasks.add(task);
        Logger.d(this.TAG, "File #" + task.uniqId + "| Requested");
        this.updateFileQueueStates();
        return task.taskId;
    }

    private synchronized UploadTask[] getActiveTasks() {
        ArrayList<UploadTask> res = new ArrayList<UploadTask>();
        for (UploadTask task : this.tasks) {
            if (task.state != 1) continue;
            res.add(task);
        }
        return res.toArray(new UploadTask[res.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void updateFileQueueStates() {
        UploadTask[] activeTasks;
        block3 : for (UploadTask task : activeTasks = this.getActiveTasks()) {
            for (UploadBlock block : task.blocks) {
                if (block.state != 2) continue block3;
            }
            this.onTaskCompleted(task);
        }
        activeTasks = this.getActiveTasks();
        int count = activeTasks.length;
        while (count < 2) {
            long mintime = Long.MAX_VALUE;
            UploadTask minTask = null;
            for (UploadTask task : this.tasks) {
                if (task.state != 0 || task.queueTime >= mintime) continue;
                minTask = task;
            }
            if (minTask == null) break;
            minTask.state = 1;
            Logger.d(this.TAG, "File #" + minTask.uniqId + "| Uploading");
        }
        Object mintime = this.threadLocker;
        synchronized (mintime) {
            this.threadLocker.notifyAll();
        }
    }

    private synchronized void onTaskCompleted(UploadTask task) {
        if (task.state != 2) {
            Logger.d(this.TAG, "File #" + task.uniqId + "| Completed in " + (System.nanoTime() - task.queueTime) / 1000000L + " ms");
            task.state = 2;
            try {
                if (task.file != null) {
                    task.file.close();
                    task.file = null;
                }
                if (task.listener != null) {
                    task.listener.onUploaded(task);
                }
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
        }
        this.updateFileQueueStates();
    }

    private synchronized UploadTask fetchTask() {
        UploadTask[] activeTasks = this.getActiveTasks();
        if (activeTasks.length == 0) {
            return null;
        }
        if (activeTasks.length == 1) {
            return activeTasks[0];
        }
        return activeTasks[this.rnd.nextInt(activeTasks.length)];
    }

    private synchronized UploadBlock fetchBlock() {
        UploadTask task = this.fetchTask();
        if (task == null) {
            return null;
        }
        for (int i = 0; i < task.blocks.length; ++i) {
            if (task.blocks[i].state != 0) continue;
            task.blocks[i].state = 1;
            byte[] block = new byte[Math.min(task.size - task.blockSize * i, task.blockSize)];
            try {
                task.file.seek(task.blockSize * i);
                task.file.readFully(block);
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
            task.blocks[i].workData = block;
            return task.blocks[i];
        }
        return null;
    }

    private synchronized void onBlockUploaded(UploadBlock block) {
        block.state = 2;
        if (block.task.listener != null) {
            int downloadedCount = 0;
            for (UploadBlock b : block.task.blocks) {
                if (b.state != 2) continue;
                ++downloadedCount;
            }
            int percent = downloadedCount * 100 / block.task.blocks.length;
            block.task.listener.onPartUploaded(percent, downloadedCount);
        }
        this.updateFileQueueStates();
    }

    private synchronized void onBlockFailure(UploadBlock block) {
        block.state = 0;
        this.updateFileQueueStates();
    }

    private class UploadFileThread
    extends Thread {
        public UploadFileThread() {
            this.setName("UploadFileThread#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            do {
                Logger.d(Uploader.this.TAG, "UploadFileThread iteration");
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                UploadBlock block = Uploader.this.fetchBlock();
                if (block == null) {
                    Object object = Uploader.this.threadLocker;
                    synchronized (object) {
                        try {
                            Uploader.this.threadLocker.wait();
                            continue;
                        }
                        catch (InterruptedException e) {
                            Logger.e(Uploader.this.TAG, e);
                            return;
                        }
                    }
                }
                long start = System.nanoTime();
                Logger.d(Uploader.this.TAG, "Block #" + block.index + " of #" + block.task.uniqId + "| Starting");
                try {
                    if (block.task.usedBigFile) {
                        Uploader.this.api.doSaveBigFilePart(block.task.uniqId, block.index, block.task.blocks.length, block.workData);
                    } else {
                        Uploader.this.api.doSaveFilePart(block.task.uniqId, block.index, block.workData);
                    }
                    block.workData = null;
                    Logger.d(Uploader.this.TAG, "Block #" + block.index + " of #" + block.task.uniqId + "| Uploaded in " + (System.nanoTime() - start) / 1000000L + " ms");
                    Uploader.this.onBlockUploaded(block);
                    continue;
                }
                catch (IOException | TimeoutException e) {
                    Logger.d(Uploader.this.TAG, "Block #" + block.index + " of #" + block.task.uniqId + "| Failure");
                    Logger.e(Uploader.this.TAG, e);
                    Uploader.this.onBlockFailure(block);
                    continue;
                }
                break;
            } while (true);
        }
    }

    private class UploadBlock {
        public UploadTask task;
        public int state;
        public int index;
        public byte[] workData;

        private UploadBlock() {
        }
    }

    public class UploadTask {
        public UploadListener listener;
        public boolean usedBigFile;
        public long uniqId;
        public int taskId;
        public int blockSize;
        public long queueTime;
        public int state;
        public int size;
        public UploadBlock[] blocks;
        public String srcFile;
        public RandomAccessFile file;
        public String hash;
        public int blockCount;
    }

    public static class UploadResult {
        private long fileId;
        private boolean usedBigFile;
        private int partsCount;
        private String hash;

        public UploadResult(long fileId, int partsCount, String hash, boolean usedBigFile) {
            this.fileId = fileId;
            this.partsCount = partsCount;
            this.hash = hash;
            this.usedBigFile = usedBigFile;
        }

        public long getFileId() {
            return this.fileId;
        }

        public boolean isUsedBigFile() {
            return this.usedBigFile;
        }

        public int getPartsCount() {
            return this.partsCount;
        }

        public String getHash() {
            return this.hash;
        }
    }

}

