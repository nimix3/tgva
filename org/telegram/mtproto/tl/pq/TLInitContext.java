
package org.telegram.mtproto.tl.pq;

import org.telegram.mtproto.tl.pq.ClientDhInner;
import org.telegram.mtproto.tl.pq.DhGenFailure;
import org.telegram.mtproto.tl.pq.DhGenOk;
import org.telegram.mtproto.tl.pq.DhGenRetry;
import org.telegram.mtproto.tl.pq.MTRpcReqError;
import org.telegram.mtproto.tl.pq.ReqDhParams;
import org.telegram.mtproto.tl.pq.ReqPQ;
import org.telegram.mtproto.tl.pq.ReqSetDhClientParams;
import org.telegram.mtproto.tl.pq.ResPQ;
import org.telegram.mtproto.tl.pq.ServerDhFailure;
import org.telegram.mtproto.tl.pq.ServerDhInner;
import org.telegram.mtproto.tl.pq.ServerDhOk;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLInitContext
extends TLContext {
    @Override
    protected void init() {
        this.registerClass(1615239032, ReqPQ.class);
        this.registerClass(85337187, ResPQ.class);
        this.registerClass(-686627650, ReqDhParams.class);
        this.registerClass(-790100132, ServerDhOk.class);
        this.registerClass(2043348061, ServerDhFailure.class);
        this.registerClass(-1249309254, ServerDhInner.class);
        this.registerClass(1003222836, DhGenOk.class);
        this.registerClass(-1499615742, DhGenFailure.class);
        this.registerClass(1188831161, DhGenRetry.class);
        this.registerClass(-184262881, ReqSetDhClientParams.class);
        this.registerClass(1715713620, ClientDhInner.class);
        this.registerClass(2061775605, MTRpcReqError.class);
    }
}

