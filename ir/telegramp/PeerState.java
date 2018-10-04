
package ir.telegramp;

public class PeerState {
    private int id;
    private boolean isUser;
    private int messagesSent;
    private long lastMessageSentTime;
    private boolean isForwardingEnabled;
    private boolean isSpamEnabled;
    private int messageSendDelay;

    public PeerState(int id, boolean isUser) {
        this.id = id;
        this.isUser = isUser;
    }

    public int getId() {
        return this.id;
    }

    public boolean isUser() {
        return this.isUser;
    }

    public boolean isForwardingEnabled() {
        return this.isForwardingEnabled;
    }

    public void setForwardingEnabled(boolean isForwardingEnabled) {
        this.isForwardingEnabled = isForwardingEnabled;
    }

    public boolean isSpamEnabled() {
        return this.isSpamEnabled;
    }

    public void setSpamEnabled(boolean isSpamEnabled) {
        this.isSpamEnabled = isSpamEnabled;
    }

    public int getMessageSendDelay() {
        return this.messageSendDelay;
    }

    public void setMessageSendDelay(int messageSendDelay) {
        this.messageSendDelay = messageSendDelay;
    }

    public int getMessagesSent() {
        return this.messagesSent;
    }

    public void setMessagesSent(int messagesSent) {
        this.messagesSent = messagesSent;
    }

    public long getLastMessageSentTime() {
        return this.lastMessageSentTime;
    }

    public void setLastMessageSentTime(long lastMessageSentTime) {
        this.lastMessageSentTime = lastMessageSentTime;
    }
}

