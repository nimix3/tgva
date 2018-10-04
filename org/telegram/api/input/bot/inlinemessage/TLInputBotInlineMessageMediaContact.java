
package org.telegram.api.input.bot.inlinemessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.input.bot.inlinemessage.TLAbsInputBotInlineMessage;
import org.telegram.api.keyboard.replymarkup.TLAbsReplyMarkup;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLInputBotInlineMessageMediaContact
extends TLAbsInputBotInlineMessage {
    public static final int CLASS_ID = 766443943;
    private static final int FLAG_UNUSED0 = 1;
    private static final int FLAG_UNUSED1 = 2;
    private static final int FLAG_REPLY_MARKUP = 4;
    private int flags;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private TLAbsReplyMarkup replyMarkup;

    public int getFlags() {
        return this.flags;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public TLAbsReplyMarkup getReplyMarkup() {
        return this.replyMarkup;
    }

    @Override
    public int getClassId() {
        return 766443943;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeTLString(this.phoneNumber, stream);
        StreamingUtils.writeTLString(this.firstName, stream);
        StreamingUtils.writeTLString(this.lastName, stream);
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLObject(this.replyMarkup, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.phoneNumber = StreamingUtils.readTLString(stream);
        this.firstName = StreamingUtils.readTLString(stream);
        this.lastName = StreamingUtils.readTLString(stream);
        if ((this.flags & 4) != 0) {
            this.replyMarkup = (TLAbsReplyMarkup)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "inputBotInlineMessageMediaContact#2daf01a7";
    }
}

