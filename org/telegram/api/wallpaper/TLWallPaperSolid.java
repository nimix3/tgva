
package org.telegram.api.wallpaper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.wallpaper.TLAbsWallPaper;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLWallPaperSolid
extends TLAbsWallPaper {
    public static final int CLASS_ID = 1662091044;
    private int bgColor;

    @Override
    public int getClassId() {
        return 1662091044;
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeInt(this.bgColor, stream);
        StreamingUtils.writeInt(this.color, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.bgColor = StreamingUtils.readInt(stream);
        this.color = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "wallPaperSolid#63117f24";
    }
}

