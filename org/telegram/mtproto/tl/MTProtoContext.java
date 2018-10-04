
package org.telegram.mtproto.tl;

import org.telegram.mtproto.tl.MTBadMessageNotification;
import org.telegram.mtproto.tl.MTBadServerSalt;
import org.telegram.mtproto.tl.MTDestroySession;
import org.telegram.mtproto.tl.MTDestroySessionNone;
import org.telegram.mtproto.tl.MTDestroySessionOk;
import org.telegram.mtproto.tl.MTFutureSalt;
import org.telegram.mtproto.tl.MTFutureSalts;
import org.telegram.mtproto.tl.MTGetFutureSalts;
import org.telegram.mtproto.tl.MTHttpWait;
import org.telegram.mtproto.tl.MTMessageCopy;
import org.telegram.mtproto.tl.MTMessageDetailedInfo;
import org.telegram.mtproto.tl.MTMessagesAllInfo;
import org.telegram.mtproto.tl.MTMessagesContainer;
import org.telegram.mtproto.tl.MTMessagesStateInfo;
import org.telegram.mtproto.tl.MTMsgsAck;
import org.telegram.mtproto.tl.MTMsgsStateReq;
import org.telegram.mtproto.tl.MTNeedResendMessage;
import org.telegram.mtproto.tl.MTNewMessageDetailedInfo;
import org.telegram.mtproto.tl.MTNewSessionCreated;
import org.telegram.mtproto.tl.MTPing;
import org.telegram.mtproto.tl.MTPingDelayDisconnect;
import org.telegram.mtproto.tl.MTPong;
import org.telegram.mtproto.tl.MTRpcAnswerDropped;
import org.telegram.mtproto.tl.MTRpcAnswerDroppedRunning;
import org.telegram.mtproto.tl.MTRpcAnswerUnknown;
import org.telegram.mtproto.tl.MTRpcDropAnswer;
import org.telegram.mtproto.tl.MTRpcError;
import org.telegram.mtproto.tl.MTRpcResult;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class MTProtoContext
extends TLContext {
    @Override
    protected void init() {
        this.registerClass(2059302892, MTPing.class);
        this.registerClass(-213746804, MTPingDelayDisconnect.class);
        this.registerClass(880243653, MTPong.class);
        this.registerClass(1658238041, MTMsgsAck.class);
        this.registerClass(-1631450872, MTNewSessionCreated.class);
        this.registerClass(-1477445615, MTBadMessageNotification.class);
        this.registerClass(-307542917, MTBadServerSalt.class);
        this.registerClass(-501201412, MTDestroySessionOk.class);
        this.registerClass(1658015945, MTDestroySessionNone.class);
        this.registerClass(-530561358, MTMessageCopy.class);
        this.registerClass(-2137147681, MTNewMessageDetailedInfo.class);
        this.registerClass(661470918, MTMessageDetailedInfo.class);
        this.registerClass(2105940488, MTNeedResendMessage.class);
        this.registerClass(1945237724, MTMessagesContainer.class);
        this.registerClass(558156313, MTRpcError.class);
        this.registerClass(1491380032, MTRpcDropAnswer.class);
        this.registerClass(-212046591, MTRpcResult.class);
        this.registerClass(1579864942, MTRpcAnswerUnknown.class);
        this.registerClass(-847714938, MTRpcAnswerDroppedRunning.class);
        this.registerClass(-1539647305, MTRpcAnswerDropped.class);
        this.registerClass(-414113498, MTDestroySession.class);
        this.registerClass(-1835453025, MTHttpWait.class);
        this.registerClass(-1188971260, MTGetFutureSalts.class);
        this.registerClass(155834844, MTFutureSalt.class);
        this.registerClass(-1370486635, MTFutureSalts.class);
        this.registerClass(-1933520591, MTMessagesAllInfo.class);
        this.registerClass(81704317, MTMessagesStateInfo.class);
        this.registerClass(-630588590, MTMsgsStateReq.class);
    }
}

