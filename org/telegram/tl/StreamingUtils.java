
package org.telegram.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.mtproto.log.Logger;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLMethod;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLStringVector;
import org.telegram.tl.TLVector;

public class StreamingUtils {
    public static void writeByte(int v, OutputStream stream) throws IOException {
        stream.write(v);
    }

    public static void writeByte(byte v, OutputStream stream) throws IOException {
        stream.write(v);
    }

    public static void writeInt(int v, OutputStream stream) throws IOException {
        StreamingUtils.writeByte((byte)(v & 255), stream);
        StreamingUtils.writeByte((byte)(v >> 8 & 255), stream);
        StreamingUtils.writeByte((byte)(v >> 16 & 255), stream);
        StreamingUtils.writeByte((byte)(v >> 24 & 255), stream);
    }

    public static void writeLong(long v, OutputStream stream) throws IOException {
        StreamingUtils.writeByte((byte)(v & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 8 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 16 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 24 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 32 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 40 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 48 & 255L), stream);
        StreamingUtils.writeByte((byte)(v >> 56 & 255L), stream);
    }

    public static void writeDouble(double v, OutputStream stream) throws IOException {
        StreamingUtils.writeLong(Double.doubleToLongBits(v), stream);
    }

    public static void writeByteArray(byte[] data, OutputStream stream) throws IOException {
        stream.write(data);
    }

    public static void writeByteArray(byte[] data, int offset, int len, OutputStream stream) throws IOException {
        stream.write(data, offset, len);
    }

    public static void writeTLBool(boolean v, OutputStream stream) throws IOException {
        if (v) {
            StreamingUtils.writeInt(-1720552011, stream);
        } else {
            StreamingUtils.writeInt(-1132882121, stream);
        }
    }

    public static void writeTLString(String v, OutputStream stream) throws IOException {
        StreamingUtils.writeTLBytes(v.getBytes(), stream);
    }

    public static void writeTLBytes(byte[] v, OutputStream stream) throws IOException {
        int startOffset = 1;
        if (v.length >= 254) {
            startOffset = 4;
            StreamingUtils.writeByte(254, stream);
            StreamingUtils.writeByte(v.length & 255, stream);
            StreamingUtils.writeByte(v.length >> 8 & 255, stream);
            StreamingUtils.writeByte(v.length >> 16 & 255, stream);
        } else {
            StreamingUtils.writeByte(v.length, stream);
        }
        StreamingUtils.writeByteArray(v, stream);
        int offset = (v.length + startOffset) % 4;
        if (offset != 0) {
            int offsetCount = 4 - offset;
            StreamingUtils.writeByteArray(new byte[offsetCount], stream);
        }
    }

    public static void writeTLBytes(TLBytes v, OutputStream stream) throws IOException {
        int startOffset = 1;
        if (v.getLength() >= 254) {
            startOffset = 4;
            StreamingUtils.writeByte(254, stream);
            StreamingUtils.writeByte(v.getLength() & 255, stream);
            StreamingUtils.writeByte(v.getLength() >> 8 & 255, stream);
            StreamingUtils.writeByte(v.getLength() >> 16 & 255, stream);
        } else {
            StreamingUtils.writeByte(v.getLength(), stream);
        }
        StreamingUtils.writeByteArray(v.getData(), v.getOffset(), v.getLength(), stream);
        int offset = (v.getLength() + startOffset) % 4;
        if (offset != 0) {
            int offsetCount = 4 - offset;
            StreamingUtils.writeByteArray(new byte[offsetCount], stream);
        }
    }

    public static void writeTLObject(TLObject v, OutputStream stream) throws IOException {
        v.serialize(stream);
    }

    public static void writeTLMethod(TLMethod v, OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(v, stream);
    }

    public static void writeTLVector(TLVector v, OutputStream stream) throws IOException {
        StreamingUtils.writeTLObject(v, stream);
    }

    public static int \u00bareadInt(InputStream stream) throws IOException {
        int a = stream.read();
        if (a < 0) {
            throw new IOException();
        }
        int b = stream.read();
        if (b < 0) {
            throw new IOException();
        }
        int c = stream.read();
        if (c < 0) {
            throw new IOException();
        }
        int d = stream.read();
        if (d < 0) {
            throw new IOException();
        }
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public static long readUInt(InputStream stream) throws IOException {
        long a = stream.read();
        if (a < 0L) {
            throw new IOException();
        }
        long b = stream.read();
        if (b < 0L) {
            throw new IOException();
        }
        long c = stream.read();
        if (c < 0L) {
            throw new IOException();
        }
        long d = stream.read();
        if (d < 0L) {
            throw new IOException();
        }
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public static long readLong(InputStream stream) throws IOException {
        long a = StreamingUtils.readUInt(stream);
        long b = StreamingUtils.readUInt(stream);
        return a + (b << 32);
    }

    public static double readDouble(InputStream stream) throws IOException {
        return Double.longBitsToDouble(StreamingUtils.readLong(stream));
    }

    public static String readTLString(InputStream stream) throws IOException {
        return new String(StreamingUtils.readTLBytes(stream));
    }

    public static TLObject readTLObject(InputStream stream, TLContext context) throws IOException {
        return context.deserializeMessage(stream);
    }

    public static TLMethod readTLMethod(InputStream stream, TLContext context) throws IOException {
        return (TLMethod)context.deserializeMessage(stream);
    }

    public static byte[] readBytes(int count, InputStream stream) throws IOException {
        byte[] res = new byte[count];
        int offset = 0;
        while (offset < res.length) {
            int readed = stream.read(res, offset, res.length - offset);
            if (readed > 0) {
                offset += readed;
                continue;
            }
            if (readed < 0) {
                throw new IOException();
            }
            Thread.yield();
        }
        return res;
    }

    public static void skipBytes(int count, InputStream stream) throws IOException {
        byte[] bytes = new byte[count];
        stream.read(bytes);
        Logger.d("SKIPED", "Skypped " + count + " bytes: " + bytes);
    }

    public static void readBytes(byte[] buffer, int offset, int count, InputStream stream) throws IOException {
        int woffset = 0;
        while (woffset < count) {
            int readed = stream.read(buffer, woffset + offset, count - woffset);
            if (readed > 0) {
                woffset += readed;
                continue;
            }
            if (readed < 0) {
                throw new IOException();
            }
            Thread.yield();
        }
    }

    public static byte[] readTLBytes(InputStream stream) throws IOException {
        int count = stream.read();
        int startOffset = 1;
        if (count >= 254) {
            count = stream.read() + (stream.read() << 8) + (stream.read() << 16);
            startOffset = 4;
        }
        byte[] raw = StreamingUtils.readBytes(count, stream);
        int offset = (count + startOffset) % 4;
        if (offset != 0) {
            int offsetCount = 4 - offset;
            StreamingUtils.skipBytes(offsetCount, stream);
        }
        return raw;
    }

    public static TLBytes readTLBytes(InputStream stream, TLContext context) throws IOException {
        int count = stream.read();
        int startOffset = 1;
        if (count >= 254) {
            count = stream.read() + (stream.read() << 8) + (stream.read() << 16);
            startOffset = 4;
        }
        TLBytes res = context.allocateBytes(count);
        StreamingUtils.readBytes(res.getData(), res.getOffset(), res.getLength(), stream);
        int offset = (count + startOffset) % 4;
        if (offset != 0) {
            int offsetCount = 4 - offset;
            StreamingUtils.skipBytes(offsetCount, stream);
        }
        return res;
    }

    public static TLVector readTLVector(InputStream stream, TLContext context) throws IOException {
        return context.deserializeVector(stream);
    }

    public static TLIntVector readTLIntVector(InputStream stream, TLContext context) throws IOException {
        return context.deserializeIntVector(stream);
    }

    public static TLLongVector readTLLongVector(InputStream stream, TLContext context) throws IOException {
        return context.deserializeLongVector(stream);
    }

    public static TLStringVector readTLStringVector(InputStream stream, TLContext context) throws IOException {
        return context.deserializeStringVector(stream);
    }

    public static boolean readTLBool(InputStream stream) throws IOException {
        int v = StreamingUtils.readInt(stream);
        if (v == -1720552011) {
            return true;
        }
        if (v == -1132882121) {
            return false;
        }
        throw new DeserializeException("Not bool value: " + Integer.toHexString(v));
    }

    public static byte[] intToBytes(int value) {
        return new byte[]{(byte)(value & 255), (byte)(value >> 8 & 255), (byte)(value >> 16 & 255), (byte)(value >> 24 & 255)};
    }

    public static byte[] longToBytes(long value) {
        return new byte[]{(byte)(value & 255L), (byte)(value >> 8 & 255L), (byte)(value >> 16 & 255L), (byte)(value >> 24 & 255L), (byte)(value >> 32 & 255L), (byte)(value >> 40 & 255L), (byte)(value >> 48 & 255L), (byte)(value >> 56 & 255L)};
    }

    public static int readInt(byte[] src) {
        return StreamingUtils.readInt(src, 0);
    }

    public static int readInt(byte[] src, int offset) {
        int a = src[offset + 0] & 255;
        int b = src[offset + 1] & 255;
        int c = src[offset + 2] & 255;
        int d = src[offset + 3] & 255;
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public static int readInt(InputStream stream) throws IOException {
        int i = 0;
        for (int j = 0; j < 4; ++j) {
            i |= stream.read() << j * 8;
        }
        return i;
    }

    public static long readUInt(byte[] src) {
        return StreamingUtils.readUInt(src, 0);
    }

    public static long readUInt(byte[] src, int offset) {
        long a = src[offset + 0] & 255;
        long b = src[offset + 1] & 255;
        long c = src[offset + 2] & 255;
        long d = src[offset + 3] & 255;
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public static long readLong(byte[] src, int offset) {
        long a = StreamingUtils.readUInt(src, offset);
        long b = StreamingUtils.readUInt(src, offset + 4);
        return (a & -1L) + ((b & -1L) << 32);
    }
}

