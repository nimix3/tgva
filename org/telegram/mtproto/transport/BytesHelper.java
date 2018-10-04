
package org.telegram.mtproto.transport;

import java.io.IOException;
import java.io.OutputStream;

class BytesHelper {
    BytesHelper() {
    }

    public static byte[] intToBytes(int value) {
        return new byte[]{(byte)(value & 255), (byte)(value >> 8 & 255), (byte)(value >> 16 & 255), (byte)(value >> 24 & 255)};
    }

    public static void writeInt(int value, OutputStream stream) throws IOException {
        stream.write((byte)(value & 255));
        stream.write((byte)(value >> 8 & 255));
        stream.write((byte)(value >> 16 & 255));
        stream.write((byte)(value >> 24 & 255));
    }

    public static void writeByte(int v, OutputStream stream) throws IOException {
        stream.write(v);
    }

    public static void writeByteArray(byte[] data, OutputStream stream) throws IOException {
        stream.write(data);
    }
}

