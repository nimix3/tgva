
package org.telegram.api.user.status;

import org.telegram.api.user.status.TLAbsUserStatus;

public class TLUserStatusEmpty
extends TLAbsUserStatus {
    public static final int CLASS_ID = 164646985;

    @Override
    public int getClassId() {
        return 164646985;
    }

    @Override
    public String toString() {
        return "userStatusEmpty#9d05049";
    }
}

