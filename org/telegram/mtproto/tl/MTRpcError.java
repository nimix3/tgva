
package org.telegram.mtproto.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTRpcError
extends TLObject {
    public static final int CLASS_ID = 558156313;
    private static final Pattern REGEXP_PATTERN = Pattern.compile("[A-Z_0-9]+");
    private int errorCode;
    private String message;

    public MTRpcError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public MTRpcError() {
    }

    public String getErrorTag() {
        if (this.message == null) {
            return "DEFAULT";
        }
        Matcher matcher = REGEXP_PATTERN.matcher(this.message);
        if (matcher.find()) {
            return matcher.group();
        }
        return "DEFAULT";
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public int getClassId() {
        return 558156313;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.errorCode, stream);
        StreamingUtils.writeTLString(this.message, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.errorCode = StreamingUtils.readInt(stream);
        this.message = StreamingUtils.readTLString(stream);
    }

    @Override
    public String toString() {
        return "rpc_error#2144ca19";
    }
}

