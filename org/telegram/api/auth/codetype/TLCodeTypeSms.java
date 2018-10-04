
package org.telegram.api.auth.codetype;

import org.telegram.api.auth.codetype.TLAbsCodeType;

public class TLCodeTypeSms
extends TLAbsCodeType {
    public static final int CLASS_ID = 1923290508;

    @Override
    public int getClassId() {
        return 1923290508;
    }

    @Override
    public String toString() {
        return "auth.codeTypeSms#72a3158c";
    }
}

