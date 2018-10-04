
package org.telegram.mtproto.tl.pq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class ServerDhInner
extends TLObject {
    public static final int CLASS_ID = -1249309254;
    protected byte[] nonce;
    protected byte[] serverNonce;
    protected int g;
    protected byte[] dhPrime;
    protected byte[] g_a;
    protected int serverTime;

    public ServerDhInner(byte[] nonce, byte[] serverNonce, int g, byte[] dhPrime, byte[] g_a, int serverTime) {
        this.nonce = nonce;
        this.serverNonce = serverNonce;
        this.g = g;
        this.dhPrime = dhPrime;
        this.g_a = g_a;
        this.serverTime = serverTime;
    }

    public ServerDhInner() {
    }

    @Override
    public int getClassId() {
        return -1249309254;
    }

    @Override
    public String toString() {
        return "serverDhInner#b5890dba";
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public byte[] getServerNonce() {
        return this.serverNonce;
    }

    public int getG() {
        return this.g;
    }

    public byte[] getDhPrime() {
        return this.dhPrime;
    }

    public byte[] getG_a() {
        return this.g_a;
    }

    public int getServerTime() {
        return this.serverTime;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeByteArray(this.nonce, stream);
        StreamingUtils.writeByteArray(this.serverNonce, stream);
        StreamingUtils.writeInt(this.g, stream);
        StreamingUtils.writeTLBytes(this.dhPrime, stream);
        StreamingUtils.writeTLBytes(this.g_a, stream);
        StreamingUtils.writeInt(this.serverTime, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.nonce = StreamingUtils.readBytes(16, stream);
        this.serverNonce = StreamingUtils.readBytes(16, stream);
        this.g = StreamingUtils.readInt(stream);
        this.dhPrime = StreamingUtils.readTLBytes(stream);
        this.g_a = StreamingUtils.readTLBytes(stream);
        this.serverTime = StreamingUtils.readInt(stream);
    }
}

