
package org.telegram.api.engine.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.api.TLApiContext;
import org.telegram.api.engine.Logger;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.engine.file.DownloadListener;
import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.api.storage.file.TLAbsFileType;
import org.telegram.api.upload.TLFile;
import org.telegram.tl.TLBytes;

public class Downloader {
    public static final int FILE_QUEUED = 0;
    public static final int FILE_DOWNLOADING = 1;
    public static final int FILE_COMPLETED = 2;
    public static final int FILE_CANCELED = 3;
    public static final int FILE_FAILURE = 4;
    private static final long DOWNLOAD_TIMEOUT = 30000L;
    private static final long DEFAULT_DELAY = 15000L;
    private static final int BLOCK_SIZE = 262144;
    private static final int PARALLEL_DOWNLOAD_COUNT = 2;
    private static final int PARALLEL_PARTS_COUNT = 4;
    private static final int BLOCK_QUEUED = 0;
    private static final int BLOCK_DOWNLOADING = 1;
    private static final int BLOCK_COMPLETED = 2;
    private final AtomicInteger fileIds = new AtomicInteger(1);
    private final String TAG;
    private final Object threadLocker = new Object();
    private TelegramApi api;
    private ArrayList<DownloadTask> tasks = new ArrayList();
    private ArrayList<DownloadFileThread> threads = new ArrayList();
    private Random rnd = new Random();

    public Downloader(TelegramApi api) {
        this.TAG = api.toString() + "#Downloader";
        this.api = api;
        for (int i = 0; i < 4; ++i) {
            DownloadFileThread thread = new DownloadFileThread();
            thread.start();
            this.threads.add(thread);
        }
    }

    public TelegramApi getApi() {
        return this.api;
    }

    private synchronized DownloadTask getTask(int taskId) {
        for (DownloadTask task : this.tasks) {
            if (task.taskId != taskId) continue;
            return task;
        }
        return null;
    }

    public synchronized void cancelTask(int taskId) {
        DownloadTask task = this.getTask(taskId);
        if (task != null && task.state != 2) {
            task.state = 3;
            Logger.d(this.TAG, "File #" + task.taskId + "| Canceled");
        }
        this.updateFileQueueStates();
    }

    public synchronized int getTaskState(int taskId) {
        DownloadTask task = this.getTask(taskId);
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
        while ((state = this.getTaskState(taskId)) != 2 && state != 4 && state != 3) {
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

    public synchronized int requestTask(int dcId, TLAbsInputFileLocation location, int size, String destFile, DownloadListener listener) {
        int blockSize = 262144;
        int totalBlockCount = (int)Math.ceil((double)size / (double)blockSize);
        DownloadTask task = new DownloadTask();
        task.listener = listener;
        task.blockSize = blockSize;
        task.destFile = destFile;
        try {
            task.file = new RandomAccessFile(destFile, "rw");
            task.file.setLength(size);
        }
        catch (FileNotFoundException e) {
            Logger.e(this.TAG, e);
        }
        catch (IOException e) {
            Logger.e(this.TAG, e);
        }
        task.taskId = this.fileIds.getAndIncrement();
        task.dcId = dcId;
        task.location = location;
        task.size = size;
        task.blocks = new DownloadBlock[totalBlockCount];
        int i = 0;
        while (i < totalBlockCount) {
            task.blocks[i] = new DownloadBlock();
            task.blocks[i].task = task;
            task.blocks[i].index = i++;
            task.blocks[i].state = 0;
        }
        task.state = 0;
        task.queueTime = System.nanoTime();
        this.tasks.add(task);
        Logger.d(this.TAG, "File #" + task.taskId + "| Requested");
        this.updateFileQueueStates();
        return task.taskId;
    }

    private synchronized DownloadTask[] getActiveTasks() {
        ArrayList<DownloadTask> res = new ArrayList<DownloadTask>();
        for (DownloadTask task : this.tasks) {
            if (task.state != 1) continue;
            res.add(task);
        }
        return res.toArray(new DownloadTask[res.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void updateFileQueueStates() {
        DownloadTask[] activeTasks;
        block3 : for (DownloadTask task : activeTasks = this.getActiveTasks()) {
            for (DownloadBlock block : task.blocks) {
                if (block.state != 2) continue block3;
            }
            this.onTaskCompleted(task);
        }
        activeTasks = this.getActiveTasks();
        int count = activeTasks.length;
        while (count < 2) {
            long mintime = Long.MAX_VALUE;
            DownloadTask minTask = null;
            for (DownloadTask task : this.tasks) {
                if (task.state != 0 || task.queueTime >= mintime) continue;
                minTask = task;
            }
            if (minTask == null) break;
            minTask.state = 1;
            Logger.d(this.TAG, "File #" + minTask.taskId + "| Downloading");
        }
        Object mintime = this.threadLocker;
        synchronized (mintime) {
            this.threadLocker.notifyAll();
        }
    }

    private synchronized void onTaskCompleted(DownloadTask task) {
        if (task.state != 2) {
            Logger.d(this.TAG, "File #" + task.taskId + "| Completed in " + (System.nanoTime() - task.queueTime) / 1000000L + " ms");
            task.state = 2;
            try {
                if (task.file != null) {
                    task.file.close();
                    task.file = null;
                }
                if (task.listener != null) {
                    task.listener.onDownloaded(task);
                }
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
        }
        this.updateFileQueueStates();
    }

    private synchronized void onTaskFailure(DownloadTask task) {
        if (task.state != 4) {
            Logger.d(this.TAG, "File #" + task.taskId + "| Failure in " + (System.nanoTime() - task.queueTime) / 1000000L + " ms");
            task.state = 4;
            try {
                if (task.file != null) {
                    task.file.close();
                    task.file = null;
                }
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
        }
        this.updateFileQueueStates();
    }

    private synchronized DownloadTask fetchTask() {
        DownloadTask[] activeTasks = this.getActiveTasks();
        if (activeTasks.length == 0) {
            return null;
        }
        if (activeTasks.length == 1) {
            return activeTasks[0];
        }
        return activeTasks[this.rnd.nextInt(activeTasks.length)];
    }

    private synchronized DownloadBlock fetchBlock() {
        DownloadTask task = this.fetchTask();
        if (task == null) {
            return null;
        }
        for (int i = 0; i < task.blocks.length; ++i) {
            if (task.blocks[i].state != 0) continue;
            task.blocks[i].state = 1;
            if (task.lastSuccessBlock == 0L) {
                task.lastSuccessBlock = System.nanoTime();
            }
            return task.blocks[i];
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void onBlockDownloaded(DownloadBlock block, TLBytes data) {
        block8 : {
            try {
                if (block.task.file != null) {
                    block.task.file.seek(block.index * block.task.blockSize);
                    block.task.file.write(data.getData(), data.getOffset(), data.getLength());
                    break block8;
                }
                return;
            }
            catch (IOException e) {
                Logger.e(this.TAG, e);
            }
            finally {
                this.api.getApiContext().releaseBytes(data);
            }
        }
        block.task.lastSuccessBlock = System.nanoTime();
        block.state = 2;
        if (block.task.listener != null) {
            int downloadedCount = 0;
            for (DownloadBlock b : block.task.blocks) {
                if (b.state != 2) continue;
                ++downloadedCount;
            }
            int percent = downloadedCount * 100 / block.task.blocks.length;
            block.task.listener.onPartDownloaded(percent, downloadedCount);
        }
        this.updateFileQueueStates();
    }

    private synchronized void onBlockFailure(DownloadBlock block) {
        block.state = 0;
        if (block.task.lastSuccessBlock != 0L && System.nanoTime() - block.task.lastSuccessBlock > 30000000000L) {
            this.onTaskFailure(block.task);
        }
        this.updateFileQueueStates();
    }

    private class DownloadFileThread
    extends Thread {
        public DownloadFileThread() {
            this.setName("DownloadFileThread#" + this.hashCode());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.setPriority(1);
            do {
                Logger.d(Downloader.this.TAG, "DownloadFileThread iteration");
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                DownloadBlock block = Downloader.this.fetchBlock();
                if (block == null) {
                    Object object = Downloader.this.threadLocker;
                    synchronized (object) {
                        try {
                            Downloader.this.threadLocker.wait();
                            continue;
                        }
                        catch (InterruptedException e) {
                            Logger.e(Downloader.this.TAG, e);
                            return;
                        }
                    }
                }
                long start = System.nanoTime();
                Logger.d(Downloader.this.TAG, "Block #" + block.index + " of #" + block.task.taskId + "| Starting");
                try {
                    TLFile file = Downloader.this.api.doGetFile(block.task.dcId, block.task.location, block.index * block.task.blockSize, block.task.blockSize);
                    Logger.d(Downloader.this.TAG, "Block #" + block.index + " of #" + block.task.taskId + "| Downloaded in " + (System.nanoTime() - start) / 1000000L + " ms");
                    if (block.task.type == null) {
                        block.task.type = file.getType();
                    }
                    Downloader.this.onBlockDownloaded(block, file.getBytes());
                    continue;
                }
                catch (IOException | TimeoutException e) {
                    Logger.d(Downloader.this.TAG, "Block #" + block.index + " of #" + block.task.taskId + "| Failure");
                    Logger.e(Downloader.this.TAG, e);
                    Downloader.this.onBlockFailure(block);
                    continue;
                }
                break;
            } while (true);
        }
    }

    private class DownloadBlock {
        public DownloadTask task;
        public int state;
        public int index;

        private DownloadBlock() {
        }
    }

    public class DownloadTask {
        public DownloadListener listener;
        public long lastNotifyTime;
        public int taskId;
        public int blockSize;
        public int dcId;
        public TLAbsInputFileLocation location;
        public int size;
        public long queueTime;
        public int state;
        public DownloadBlock[] blocks;
        public String destFile;
        public RandomAccessFile file;
        public long lastSuccessBlock;
        public TLAbsFileType type;
    }

}

