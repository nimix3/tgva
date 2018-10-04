
package org.telegram.mtproto.schedule;

public class PreparedPackage {
    private boolean isHighPriority;
    private int seqNo;
    private long messageId;
    private byte[] content;

    public PreparedPackage(int seqNo, long messageId, byte[] content, boolean isHighPriority) {
        this.seqNo = seqNo;
        this.messageId = messageId;
        this.content = content;
        this.isHighPriority = isHighPriority;
    }

    public boolean isHighPriority() {
        return this.isHighPriority;
    }

    public int getSeqNo() {
        return this.seqNo;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public byte[] getContent() {
        return this.content;
    }
}

