
package org.telegram.tl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.telegram.mtproto.log.Logger;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLVector<T>
extends TLObject
implements List<T> {
    public static final int CLASS_ID = 481674261;
    private Class destClass = TLObject.class;
    private ArrayList<T> items = new ArrayList();

    @Override
    public int getClassId() {
        return 481674261;
    }

    public Class getDestClass() {
        return this.destClass;
    }

    public void setDestClass(Class destClass) {
        if (destClass == null) {
            throw new RuntimeException("DestClass could not be null");
        }
        if (destClass != Integer.class && destClass != Long.class && destClass != String.class && !TLObject.class.isAssignableFrom(destClass)) {
            throw new RuntimeException("Unsupported DestClass");
        }
        this.destClass = destClass;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.items.size(), stream);
        if (this.destClass == Integer.class) {
            for (T i : this.items) {
                StreamingUtils.writeInt((Integer)i, stream);
            }
        } else if (this.destClass == Long.class) {
            for (T i : this.items) {
                StreamingUtils.writeLong((Long)i, stream);
            }
        } else if (this.destClass == String.class) {
            for (T i : this.items) {
                StreamingUtils.writeTLString((String)i, stream);
            }
        } else {
            for (T i : this.items) {
                StreamingUtils.writeTLObject((TLObject)i, stream);
            }
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        if (this.destClass == null) {
            throw new IOException("DestClass not set");
        }
        int count = StreamingUtils.readInt(stream);
        for (int i = 0; i < count; ++i) {
            Logger.d("TLVECTOR", "reading: " + i + " from " + count + " (" + this.items.size() + ")" + " --> " + this.destClass);
            if (this.destClass == Integer.class) {
                this.items.add(StreamingUtils.readInt(stream));
            } else if (this.destClass == Long.class) {
                this.items.add(StreamingUtils.readLong(stream));
            } else if (this.destClass == String.class) {
                this.items.add(StreamingUtils.readTLString(stream));
            } else {
                this.items.add(context.deserializeMessage(stream));
            }
            Logger.d("TLVECTOR", "Extracted " + this.items.get(i).toString());
        }
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.items.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.items.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return this.items.toArray(t1s);
    }

    @Override
    public boolean add(T t) {
        return this.items.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return this.items.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return this.items.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends T> ts) {
        return this.items.addAll(ts);
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> ts) {
        return this.items.addAll(i, ts);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return this.items.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return this.items.retainAll(objects);
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public T get(int i) {
        return this.items.get(i);
    }

    @Override
    public T set(int i, T t) {
        return this.items.set(i, t);
    }

    @Override
    public void add(int i, T t) {
        this.items.add(i, t);
    }

    @Override
    public T remove(int i) {
        return this.items.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return this.items.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.items.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.items.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return this.items.listIterator(i);
    }

    @Override
    public List<T> subList(int i, int i2) {
        return this.items.subList(i, i2);
    }

    @Override
    public String toString() {
        return "vector#1cb5c415";
    }
}

