
package org.telegram.api.message.media;

import org.telegram.api.message.media.TLAbsMessageMedia;

public class TLMessageMediaUnsupported
extends TLAbsMessageMedia {
    public static final int CLASS_ID = -1618676578;

    @Override
    public int getClassId() {
        return -1618676578;
    }

    @Override
    public String toString() {
        return "messageMediaUnsupported#9f84f49e";
    }
}

