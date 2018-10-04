
package org.telegram.tl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import org.telegram.mtproto.log.Logger;
import org.telegram.tl.DeserializeException;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLBoolFalse;
import org.telegram.tl.TLBoolTrue;
import org.telegram.tl.TLBytes;
import org.telegram.tl.TLGzipObject;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLLongVector;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLStringVector;
import org.telegram.tl.TLVector;

public abstract class TLContext {
    private static final String TAG = "TLCONTEXT";
    private final HashMap<Integer, Class> registeredClasses = new HashMap();
    private final HashMap<Integer, Class> registeredCompatClasses = new HashMap();

    public TLContext() {
        this.init();
    }

    protected void init() {
    }

    public boolean isSupportedObject(TLObject object) {
        return this.isSupportedObject(object.getClassId());
    }

    public boolean isSupportedObject(int classId) {
        return this.registeredClasses.containsKey(classId);
    }

    public <T extends TLObject> void registerClass(Class<T> tClass) {
        try {
            int classId = tClass.getField("CLASS_ID").getInt(null);
            this.registeredClasses.put(classId, tClass);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
    }

    public <T extends TLObject> void registerClass(int clazzId, Class<T> tClass) {
        if (this.registeredClasses.containsKey(clazzId)) {
            Logger.e("TelegramApi", new Exception("Class already exists" + clazzId));
        }
        this.registeredClasses.put(clazzId, tClass);
    }

    public <T extends TLObject> void registerCompatClass(Class<T> tClass) {
        try {
            int classId = tClass.getField("CLASS_ID").getInt(null);
            this.registeredCompatClasses.put(classId, tClass);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
    }

    public <T extends TLObject> void registerCompatClass(int clazzId, Class<T> tClass) {
        this.registeredCompatClasses.put(clazzId, tClass);
    }

    protected TLObject convertCompatClass(TLObject src) {
        return src;
    }

    public TLObject deserializeMessage(byte[] data) throws IOException {
        return this.deserializeMessage(new ByteArrayInputStream(data));
    }

    public TLObject deserializeMessage(int clazzId, InputStream stream) throws IOException {
        if (clazzId == 812830625) {
            TLGzipObject obj = new TLGzipObject();
            obj.deserializeBody(stream, this);
            BufferedInputStream gzipInputStream = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(obj.getPackedData())));
            int innerClazzId = StreamingUtils.readInt(gzipInputStream);
            return this.deserializeMessage(innerClazzId, gzipInputStream);
        }
        if (clazzId == -1720552011) {
            return new TLBoolTrue();
        }
        if (clazzId == -1132882121) {
            return new TLBoolFalse();
        }
        if (this.registeredCompatClasses.containsKey(clazzId)) {
            try {
                Class messageClass = this.registeredCompatClasses.get(clazzId);
                TLObject message = (TLObject)messageClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                message.deserializeBody(stream, this);
                return this.convertCompatClass(message);
            }
            catch (DeserializeException e) {
                throw e;
            }
            catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e);
                throw new IOException("Unable to deserialize data #" + Integer.toHexString(clazzId) + " #" + clazzId);
            }
        }
        try {
            Class messageClass = this.registeredClasses.get(clazzId);
            if (messageClass != null) {
                TLObject message = (TLObject)messageClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                message.deserializeBody(stream, this);
                return message;
            }
            throw new DeserializeException("Unsupported class: #" + Integer.toHexString(clazzId) + " #" + clazzId);
        }
        catch (DeserializeException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e);
            throw new IOException("Unable to deserialize data #" + Integer.toHexString(clazzId) + " #" + clazzId);
        }
    }

    public TLObject deserializeMessage(InputStream stream) throws IOException {
        int clazzId = StreamingUtils.readInt(stream);
        return this.deserializeMessage(clazzId, stream);
    }

    public TLVector deserializeVector(InputStream stream) throws IOException {
        int clazzId = StreamingUtils.readInt(stream);
        if (clazzId == 481674261) {
            TLVector res = new TLVector();
            res.deserializeBody(stream, this);
            return res;
        }
        if (clazzId == 812830625) {
            TLGzipObject obj = new TLGzipObject();
            obj.deserializeBody(stream, this);
            BufferedInputStream gzipInputStream = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(obj.getPackedData())));
            return this.deserializeVector(gzipInputStream);
        }
        throw new IOException("Unable to deserialize vector #" + Integer.toHexString(clazzId) + " #" + clazzId);
    }

    public TLIntVector deserializeIntVector(InputStream stream) throws IOException {
        int clazzId = StreamingUtils.readInt(stream);
        if (clazzId == 481674261) {
            TLIntVector res = new TLIntVector();
            res.deserializeBody(stream, this);
            return res;
        }
        if (clazzId == 812830625) {
            TLGzipObject obj = new TLGzipObject();
            obj.deserializeBody(stream, this);
            BufferedInputStream gzipInputStream = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(obj.getPackedData())));
            return this.deserializeIntVector(gzipInputStream);
        }
        throw new IOException("Unable to deserialize vector #" + Integer.toHexString(clazzId) + " #" + clazzId);
    }

    public TLLongVector deserializeLongVector(InputStream stream) throws IOException {
        int clazzId = StreamingUtils.readInt(stream);
        if (clazzId == 481674261) {
            TLLongVector res = new TLLongVector();
            res.deserializeBody(stream, this);
            return res;
        }
        if (clazzId == 812830625) {
            TLGzipObject obj = new TLGzipObject();
            obj.deserializeBody(stream, this);
            BufferedInputStream gzipInputStream = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(obj.getPackedData())));
            return this.deserializeLongVector(gzipInputStream);
        }
        throw new IOException("Unable to deserialize vector #" + Integer.toHexString(clazzId) + " #" + clazzId);
    }

    public TLStringVector deserializeStringVector(InputStream stream) throws IOException {
        int clazzId = StreamingUtils.readInt(stream);
        if (clazzId == 481674261) {
            TLStringVector res = new TLStringVector();
            res.deserializeBody(stream, this);
            return res;
        }
        if (clazzId == 812830625) {
            TLGzipObject obj = new TLGzipObject();
            obj.deserializeBody(stream, this);
            BufferedInputStream gzipInputStream = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(obj.getPackedData())));
            return this.deserializeStringVector(gzipInputStream);
        }
        throw new IOException("Unable to deserialize vector #" + Integer.toHexString(clazzId) + " #" + clazzId);
    }

    public TLBytes allocateBytes(int size) {
        return new TLBytes(new byte[size], 0, size);
    }

    public void releaseBytes(TLBytes unused) {
    }
}

