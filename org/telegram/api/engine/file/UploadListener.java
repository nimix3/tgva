
package org.telegram.api.engine.file;

import org.telegram.api.engine.file.Uploader;

public interface UploadListener {
    public void onPartUploaded(int var1, int var2);

    public void onUploaded(Uploader.UploadTask var1);
}

