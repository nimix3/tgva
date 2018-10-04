
package org.telegram.api.engine.file;

import org.telegram.api.engine.file.Downloader;

public interface DownloadListener {
    public void onPartDownloaded(int var1, int var2);

    public void onDownloaded(Downloader.DownloadTask var1);

    public void onFailed();
}

