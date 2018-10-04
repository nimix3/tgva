
package org.telegram.api.bot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLBotCommand
extends TLObject {
    public static final int CLASS_ID = -1032140601;
    private String command;
    private String description;

    @Override
    public int getClassId() {
        return -1032140601;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeTLString(this.command, stream);
        StreamingUtils.writeTLString(this.description, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.command = StreamingUtils.readTLString(stream);
        this.description = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "bot.BotCommands#c27ac8c7";
    }
}

