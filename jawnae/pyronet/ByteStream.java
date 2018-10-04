
package jawnae.pyronet;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import jawnae.pyronet.PyroException;
import org.telegram.mtproto.transport.BuffersStorage;
import org.telegram.mtproto.transport.ByteBufferDesc;

public class ByteStream {
    private final List<ByteBufferDesc> queue = new ArrayList<ByteBufferDesc>();

    public void append(ByteBufferDesc buf) {
        if (buf == null) {
            throw new NullPointerException();
        }
        this.queue.add(buf);
    }

    public boolean hasData() {
        int size = this.queue.size();
        for (ByteBufferDesc aQueue : this.queue) {
            if (!aQueue.hasRemaining()) continue;
            return true;
        }
        return false;
    }

    public void get(ByteBuffer dst) {
        if (dst == null) {
            throw new NullPointerException();
        }
        for (ByteBufferDesc bufferDesc : this.queue) {
            ByteBuffer data = bufferDesc.buffer.slice();
            if (data.remaining() > dst.remaining()) {
                data.limit(dst.remaining());
                dst.put(data);
                break;
            }
            dst.put(data);
            if (dst.hasRemaining()) continue;
            break;
        }
    }

    public void discard(int count) {
        int original = count;
        while (count > 0) {
            ByteBufferDesc data = this.queue.get(0);
            if (count < data.buffer.remaining()) {
                data.position(data.position() + count);
                count = 0;
                break;
            }
            this.queue.remove(0);
            BuffersStorage.getInstance().reuseFreeBuffer(data);
            count -= data.buffer.remaining();
        }
        if (count != 0) {
            throw new PyroException("discarded " + (original - count) + "/" + original + " bytes");
        }
    }
}

