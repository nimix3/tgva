
package org.telegram.mtproto.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import org.telegram.mtproto.log.Logger;
import org.telegram.tl.StreamingUtils;

public class PlainTcpConnection {
    private static final String TAG = "PlainTcpConnection";
    private static final int CONNECTION_TIMEOUT = 5000;
    private Socket socket = new Socket();
    private boolean isBroken;

    public PlainTcpConnection(String ip, int port) throws IOException {
        this.socket.connect(new InetSocketAddress(ip, port), 5000);
        this.socket.setKeepAlive(true);
        this.socket.setTcpNoDelay(true);
        this.socket.getOutputStream().write(239);
        this.isBroken = false;
    }

    public Socket getSocket() {
        return this.socket;
    }

    private byte[] readMessage() throws IOException {
        InputStream stream = this.socket.getInputStream();
        int headerLen = this.readByte(stream);
        if (headerLen == 127) {
            headerLen = this.readByte(stream) + (this.readByte(stream) << 8) + (this.readByte(stream) << 16);
        }
        int len = headerLen * 4;
        return StreamingUtils.readBytes(len, stream);
    }

    private void writeMessage(byte[] request) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (request.length / 4 >= 127) {
            int len = request.length / 4;
            StreamingUtils.writeByte(127, (OutputStream)stream);
            StreamingUtils.writeByte(len & 255, (OutputStream)stream);
            StreamingUtils.writeByte(len >> 8 & 255, (OutputStream)stream);
            StreamingUtils.writeByte(len >> 16 & 255, (OutputStream)stream);
        } else {
            StreamingUtils.writeByte(request.length / 4, (OutputStream)stream);
        }
        StreamingUtils.writeByteArray(request, stream);
        byte[] pkg = stream.toByteArray();
        this.socket.getOutputStream().write(pkg, 0, pkg.length);
        this.socket.getOutputStream().flush();
    }

    public byte[] executeMethod(byte[] request) throws IOException {
        this.writeMessage(request);
        return this.readMessage();
    }

    public void destroy() {
        try {
            this.socket.close();
        }
        catch (IOException e) {
            Logger.e(TAG, e);
        }
    }

    private int readByte(InputStream stream) throws IOException {
        int res = stream.read();
        if (res < 0) {
            throw new IOException();
        }
        return res;
    }
}

