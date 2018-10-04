
package org.telegram.api.engine;

import org.telegram.api.engine.TelegramApi;
import org.telegram.api.updates.TLAbsUpdates;

public interface ApiCallback {
    public void onAuthCancelled(TelegramApi var1);

    public void onUpdatesInvalidated(TelegramApi var1);

    public void onUpdate(TLAbsUpdates var1);
}

