package myyuk.exam.channel;

import myyuk.exam.exception.StreamExecutionException;
import myyuk.exam.stream.DataWrapper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * MemoryFifoChannel is the simple channel using the blocking queue.
 * Depending on the number of parallelism, the internal queue can be replicated.
 * Implement clone to create an internal queue as a new reference.
 */
@SuppressWarnings("unused")
public class MemoryFifoChannel<T> extends Channel<T> {

    private BlockingQueue<DataWrapper<T>> queue;

    public MemoryFifoChannel() {
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public boolean add(DataWrapper<T> value) {
        return this.queue.add(value);
    }

    @Override
    public DataWrapper<T> get() {
        try {
            return this.queue.take();
        } catch (InterruptedException e) {
            throw new StreamExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public void clear() {
        if (this.queue != null) {
            this.queue.clear();
        }
    }

    @Override
    public MemoryFifoChannel<T> clone() throws CloneNotSupportedException {
        MemoryFifoChannel<T> channelClone = (MemoryFifoChannel<T>) super.clone();
        channelClone.queue = new LinkedBlockingQueue<>();
        return channelClone;
    }
}
